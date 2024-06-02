package org.tpr.auth.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tpr.auth.models.enums.UserRole;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String middleName;

    private List<UserRole> roles;

    private Date archiveDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map((item) -> new SimpleGrantedAuthority("ROLE_" + item.toString())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.isNull(archiveDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.isNull(archiveDate);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.isNull(archiveDate);
    }
}
