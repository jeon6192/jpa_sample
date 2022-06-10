package com.example.jpa_test.model.entity;

import com.example.jpa_test.model.enums.UserAuthority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_idx")
    private Long idx;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private UserAuthority userAuthority;

    @Builder
    public Role(Long idx, UserAuthority userAuthority) {
        this.idx = idx;
        this.userAuthority = userAuthority;
    }
}
