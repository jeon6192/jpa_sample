package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Fam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamRepository extends JpaRepository<Fam, Long> {
}
