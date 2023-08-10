package just.springsecurity.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void encodePassword() {
        password = "{noop}" + password;
    }
}
