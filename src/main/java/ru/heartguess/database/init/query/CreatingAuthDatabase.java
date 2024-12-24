package ru.heartguess.database.init.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.heartguess.database.config.JdbcTemplateQualifier;

@Component
public class CreatingAuthDatabase {

    @Autowired
    @Qualifier(JdbcTemplateQualifier.AUTH_JDBC_TEMPLATE)
    private JdbcTemplate jdbcTemplate;

    public void createAuthTableQuery() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS oauth2_registered_client  (
                    id varchar(100) NOT NULL,
                    client_id varchar(100) NOT NULL,
                    client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                    client_secret varchar(200) DEFAULT NULL,
                    client_secret_expires_at timestamp DEFAULT NULL,
                    client_name varchar(200) NOT NULL,
                    client_authentication_methods varchar(1000) NOT NULL,
                    authorization_grant_types varchar(1000) NOT NULL,
                    redirect_uris varchar(1000) DEFAULT NULL,
                    post_logout_redirect_uris varchar(1000) DEFAULT NULL,
                    scopes varchar(1000) NOT NULL,
                    client_settings varchar(2000) NOT NULL,
                    token_settings varchar(2000) NOT NULL,
                    PRIMARY KEY (id)
                );
                
                CREATE TABLE IF NOT EXISTS oauth2_authorization_consent (
                    registered_client_id varchar(100) NOT NULL,
                    principal_name varchar(200) NOT NULL,
                    authorities varchar(1000) NOT NULL,
                    PRIMARY KEY (registered_client_id, principal_name)
                );
                
                CREATE TABLE IF NOT EXISTS users (
                    username varchar(200) not null,
                    password varchar(500) not null,
                    enabled boolean not null,
                    PRIMARY KEY (username)
                );
                
                CREATE TABLE IF NOT EXISTS authorities (
                    username varchar(200) not null,
                    authority varchar(200) not null,
                    constraint fk_authorities_users foreign key(username) references users(username)
                );
                """);
    }
}
