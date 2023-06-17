package gwkim.storemanage.member.service;

import gwkim.storemanage.api.member.exception.custom.DuplicatedMemberException;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.repository.MemberRepository;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import gwkim.storemanage.member.utils.MemberSignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberSignUpValidator memberSignUpValidator;

    public Page<Member> findBySearchOption(Pageable pageable, MemberSearchCondition memberSearchCondition) {

        return memberRepository.findBySearchOption(pageable, memberSearchCondition);
    }

    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    @Transactional(readOnly = false)
    public String save(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    /**
     * 회원 아이디 종복 체크
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        // 동시성 문제를 예방하기 위해 db에 유니크 제약조건을 걸어주는 것을 권장함..
        Optional<Member> existingMember = memberRepository.findById(member.getId());

        if (existingMember.isPresent()) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다. 회원 아이디: " + member.getId());
        }
    }

    @Transactional(readOnly = false)
    public String update(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());

        if(findMember.isPresent()){
            findMember.get()
                    .builder()
                    .name(member.getName());
        }
        return findMember.get().getId();
    }

}
