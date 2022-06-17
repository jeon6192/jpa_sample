package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.CryptoTest;
import com.example.jpa_test.model.entity.QCryptoTest;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

@Repository
public class CryptoTestRepositoryImpl {

    QCryptoTest qCryptoTest = QCryptoTest.cryptoTest;

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    public CryptoTestRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public CryptoTest select(Long idx) {
        return queryFactory.select(qCryptoTest)
                .from(qCryptoTest)
                .where(qCryptoTest.idx.eq(idx))
                .fetchOne();
    }

    public CryptoTest save(CryptoTest cryptoTest) {
        Long idx = queryFactory.insert(qCryptoTest)
                .columns(qCryptoTest.algorithm, qCryptoTest.origin, qCryptoTest.cryptoText, qCryptoTest.secretKey, qCryptoTest.iv)
                .values(cryptoTest.getAlgorithm(), cryptoTest.getOrigin(),
//                        Expressions.stringTemplate("encrypt({0})", cryptoTest.getCryptoText()),
                        cryptoTest.getCryptoText(),
                        cryptoTest.getSecretKey(), cryptoTest.getIv())
                .execute();

        return select(idx);
    }

    @Transactional
    public void save2(CryptoTest cryptoTest) {
        String sql = "INSERT INTO crypto_test (algorithm, origin, crypto_text, secret_key) VALUES (:algorithm, :origin, encrypt(:cryptoText), :secretKey)";
        Query query = em.createNativeQuery(sql, CryptoTest.class);
        query.setParameter("algorithm", cryptoTest.getAlgorithm());
        query.setParameter("origin", cryptoTest.getOrigin());
        query.setParameter("cryptoText", cryptoTest.getCryptoText());
        query.setParameter("secretKey", cryptoTest.getSecretKey());

        query.executeUpdate();
    }

}
