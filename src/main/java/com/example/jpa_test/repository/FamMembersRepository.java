package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Fam;
import com.example.jpa_test.model.entity.FamMembers;
import com.example.jpa_test.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamMembersRepository extends JpaRepository<FamMembers, Long> {
    List<FamMembers> findByFam(Fam fam);

    Optional<FamMembers> findByFamAndMember(Fam fam, Member member);
}
