package com.example.jpa_test.config.security.guard;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.entity.Fam;
import com.example.jpa_test.model.entity.FamMembers;
import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.repository.FamMembersRepository;
import com.example.jpa_test.repository.FamRepository;
import com.example.jpa_test.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("famGuard")
public class FamGuard {

    private final MemberRepository memberRepository;
    private final FamRepository famRepository;
    private final FamMembersRepository famMembersRepository;

    public FamGuard(MemberRepository memberRepository, FamRepository famRepository, FamMembersRepository famMembersRepository) {
        this.memberRepository = memberRepository;
        this.famRepository = famRepository;
        this.famMembersRepository = famMembersRepository;
    }

    public final boolean checkAuthority(Long famIdx) throws UserException {
        if (famIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        Fam fam = getFam(famIdx);

        return hasRole(famIdx) && getFamMembersByFam(fam).size() == 1;
    }

    public final boolean checkAuthority(Long famIdx, Long memberIdx) throws UserException {
        if (famIdx == null || memberIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return hasRole(famIdx) || isOwner(memberIdx);
    }

    protected boolean isOwner(Long memberIdx) {
        return Objects.equals(memberIdx, GuardHelper.extractMemberIdx());
    }

    private boolean hasRole(Long famIdx) throws UserException {
        Long extractMemberIdx = GuardHelper.extractMemberIdx();

        Member member = getMember(extractMemberIdx);
        Fam fam = getFam(famIdx);
        FamMembers famMembers = getFamMembers(fam, member);

        return famMembers.getIsMaster();
    }

    private Member getMember(Long memberIdx) throws UserException {
        return memberRepository.findById(memberIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
    }

    private Fam getFam(Long famIdx) throws UserException {
        return famRepository.findById(famIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
    }

    private FamMembers getFamMembers(Fam fam, Member member) throws UserException {
        return famMembersRepository.findByFamAndMember(fam, member)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
    }

    private List<FamMembers> getFamMembersByFam(Fam fam) {
        return famMembersRepository.findByFam(fam);
    }
}
