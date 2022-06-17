package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.CryptoTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoTestRepository extends JpaRepository<CryptoTest, Long> {
    @Query(value = "SELECT set_db_key(:key);", nativeQuery = true)
    void setAesKey(String key);

    @Query(value = "SELECT encrypt(:value);", nativeQuery = true)
    String encrypt(String value);

    @Query(value = "SELECT decrypt(:value);", nativeQuery = true)
    String decrypt(String value);
}
