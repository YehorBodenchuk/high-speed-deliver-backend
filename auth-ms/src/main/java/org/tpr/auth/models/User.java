package org.tpr.auth.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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
public class User implements UserDetails {

    @Id
    private String id;

    private String email;

    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String middleName;

    private List<UserRole> roles;

    private Date archiveDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map((item) -> new SimpleGrantedAuthority(item.toString())).toList();
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
        return Objects.nonNull(archiveDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
