package ru.heart.guess.heartguess.oauthserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class NewUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = -1749846211212457229L;

    @Size(min = 3, max = 15, message = "Длина имени пользователя должна составлять от 3 до 15 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "Разрешены только буквы нижнего и верхнего регистра, а также цифры")
    @JsonProperty("username")
    private String username;

    @Size(min = 5, max = 15, message = "Длина пароля должна составлять от 5 до 15 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]*", message = "Разрешены только буквы нижнего и верхнего регистра, а также цифры")
    @JsonProperty("password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
