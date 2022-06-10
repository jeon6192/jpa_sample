package com.example.jpa_test.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long idx;

    @Column(unique = true)
    private String memberId;

    private String password;

    private String birth;

    @Embedded
    private Name name;

    private String phone;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_idx"),
            inverseJoinColumns = @JoinColumn(name = "role_idx")
    )
    private Set<Role> roles = new HashSet<>();

    @Builder
    public Member(Long idx, String memberId, String password, String birth, Name name, String phone, Set<Role> roles) {
        this.idx = idx;
        this.memberId = memberId;
        this.password = password;
        this.birth = birth;
        this.name = name;
        this.phone = phone;
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
