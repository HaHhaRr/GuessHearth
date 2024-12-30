package ru.heartguess.oauthserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser implements UserDetails {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 15;

    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 15;

    @Serial
    private static final long serialVersionUID = -1749846211212457229L;

    @Size(
            min = MIN_USERNAME_LENGTH,
            max = MAX_USERNAME_LENGTH,
            message = "Длина имени пользователя должна составлять от 3 до 15 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "Разрешены только буквы нижнего и верхнего регистра, а также цифры")
    @JsonProperty("username")
    private String username;

    @Size(
            min = MIN_PASSWORD_LENGTH,
            max = MAX_PASSWORD_LENGTH,
            message = "Длина пароля должна составлять от 5 до 15 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "Разрешены только буквы нижнего и верхнего регистра, а также цифры")
    @JsonProperty("password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
