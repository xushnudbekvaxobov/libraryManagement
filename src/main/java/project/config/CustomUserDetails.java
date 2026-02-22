package project.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.enums.UserRole;
import project.enums.UserStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private  String username;
    private  String password;
    private UserRole role;
    private UserStatus status;

    public CustomUserDetails(String username, String password, UserRole role, UserStatus status){
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.name()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {    ///  akkaunt muddati tugagaganmi yoki yoq
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {     /// parol muddati tugaganmi yoki yoq
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
