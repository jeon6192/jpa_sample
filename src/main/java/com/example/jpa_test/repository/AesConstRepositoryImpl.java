package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.CryptoTest;
import com.example.jpa_test.model.entity.QAesConst;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class AesConstRepositoryImpl {

    QAesConst qAesConst = QAesConst.aesConst;

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    public AesConstRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public String encrypt(String data) {
        String sql = "SELECT encrypt(:data)";
        Query query = em.createNativeQuery(sql, String.class);
        query.setParameter("data", data);

        return (String) query.getSingleResult();
    }

    public String decrypt(String data) {
        String sql = "SELECT decrypt(:data)";
        Query query = em.createNativeQuery(sql, String.class);
        query.setParameter("data", data);

        return (String) query.getSingleResult();
    }
}
