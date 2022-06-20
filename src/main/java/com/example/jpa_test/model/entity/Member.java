package com.example.jpa_test.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(columnDefinition = "integer default 0")
    private Integer passwordFailCount;

    @Column(columnDefinition = "boolean default false")
    private Boolean isLocked;

    private LocalDateTime lockedDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_idx"),
            inverseJoinColumns = @JoinColumn(name = "role_idx")
    )
    private Set<Role> roles = new HashSet<>();

    @Builder
    public Member(Long idx, String memberId, String password, String birth, Name name, String phone,
                  Integer passwordFailCount, Boolean isLocked, LocalDateTime lockedDate, Set<Role> roles) {
        this.idx = idx;
        this.memberId = memberId;
        this.password = password;
        this.birth = birth;
        this.name = name;
        this.phone = phone;
        this.passwordFailCount = passwordFailCount;
        this.isLocked = isLocked;
        this.lockedDate = lockedDate;
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

    public void resetAuthenticationInfo() {
        this.passwordFailCount = 0;
        this.isLocked = false;
        this.lockedDate = null;
    }

    public void updatePasswordFailCount(Integer passwordFailCount) {
        this.passwordFailCount = passwordFailCount;
    }

    public void updateLockedInfo(boolean isLocked, LocalDateTime lockedDate) {
        this.isLocked = isLocked;
        this.lockedDate = lockedDate;
    }

    public boolean isBeforeLockedDate() {
        return this.lockedDate != null && this.lockedDate.isBefore(LocalDateTime.now());
    }
}
