package com.example.jpa_test.model.entity;

import lombok.*;

import javax.persistence.*;

@EntityListeners({Fam.class})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Fam extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fam_idx")
    private Long idx;

    @Column(unique = true)
    private String name;

    private String description;

    @Builder
    public Fam(Long idx, String name, String description) {
        this.idx = idx;
        this.name = name;
        this.description = description;
    }
}

