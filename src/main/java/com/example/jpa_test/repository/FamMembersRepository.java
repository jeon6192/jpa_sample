package com.example.jpa_test.repository;

import com.example.jpa_test.model.entity.Fam;
import com.example.jpa_test.model.entity.FamMembers;
import com.example.jpa_test.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// findById(), save(), delete() 등 간단한 메서드는 JpaRepository 내에 포함 되어 있음.
// entity 에 정의된 필드는 jpa 키워드를 통해 메서드 선언만 해주어도 동작 함.
/**
 * @see <a href="https://happygrammer.tistory.com/158">Link</a>
 */
@Repository
public interface FamMembersRepository extends JpaRepository<FamMembers, Long> {
    List<FamMembers> findByFam(Fam fam);

    Optional<FamMembers> findByFamAndMember(Fam fam, Member member);
}
