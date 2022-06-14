package com.example.jpa_test.config.security;

import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.Role;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// UserDetails 를 상속하여 Override 한 메소드들은 member 테이블에 인증정보가 추가 될 때 마다 Override 한 메소드 구현부를 구현해 주어야함.
// ex) 테이블에 isExpired 컬럼을 추가 할 시 isAccountNonExpired() 에서 해당 DB 컬럼 값을 통해 정확한 값이 리턴 되도록 수정 해야함.
public class CustomUserDetails implements UserDetails {

    // 로그인 시 필요한 컬럼 memberId, password, passwordFailCount, isLocked, lockedDate 컬럼만 생성 해주어도 됨
    private final Member member;

    public Member getMember() {
        return this.member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = member.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getUserAuthority().name()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getIsLocked() == null || ! member.getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public CustomUserDetails(Member member) {
        this.member = member;
    }
}
