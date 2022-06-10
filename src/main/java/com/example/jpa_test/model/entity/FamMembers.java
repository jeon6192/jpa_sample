package com.example.jpa_test.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "fam_idx", "member_idx" }) })
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamMembers extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToOne
    @JoinColumn(name = "fam_idx")
    private Fam fam;

    @OneToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    private Boolean isMaster;

    private String description;

    @Builder
    public FamMembers(Long idx, Fam fam, Member member, Boolean isMaster, String description) {
        this.idx = idx;
        this.fam = fam;
        this.member = member;
        this.isMaster = isMaster;
        this.description = description;
    }
}
