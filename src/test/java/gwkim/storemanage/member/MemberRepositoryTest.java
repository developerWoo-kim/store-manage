package gwkim.storemanage.member;

import gwkim.storemanage.common.domain.Address;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.repository.MemberRepository;
import gwkim.storemanage.member.service.MemberService;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원정보 조회_페이징 테스트")
    public void findPagingTest() throws Exception {
        //given
        PageRequest pageable = PageRequest.of(7, 5, Sort.Direction.DESC, "id");
        Page<Member> list = memberRepository.findBySearchOption(pageable, new MemberSearchCondition());
        //when
        System.out.println("PAGE SIZE: " + list.getSize());
        System.out.println("TOTAL PAGE: " + list.getTotalPages());
        System.out.println("TOTAL COUNT: " + list.getTotalElements());
        System.out.println("NEXT: " + list.nextPageable());
        System.out.println("list.getPageable().getPageNumber(): " + list.getPageable().getPageNumber());
        System.out.println("getPageNumber: " + list.nextPageable().getPageNumber());
        System.out.println("isPaged: " + list.nextPageable().isPaged());
        System.out.println("getPageSize: " + list.nextPageable().getPageSize());
        System.out.println("getOffset: " + list.nextPageable().getOffset());
        System.out.println("first: " + list.nextPageable().first().getPageNumber());

        System.out.println("NEXT: " + list.nextPageable().next().getPageNumber());

        System.out.println("NEXT: " + list.nextPageable().previousOrFirst().getPageNumber());

        System.out.println("==========");
        System.out.println(list.getNumber());
        //then
        assertThat(list.getTotalElements()).isEqualTo(2L);
    }

    @Test
    @DisplayName("회원가입 테스트")
    @Rollback(value = false)
    public void signupTest() throws Exception {
        //given
        Member member = new Member().builder()
                .id("abcd1234")
                .name("김건우")
                .password("0000")
                .phoneNum("01000000000")
                .email("rjsdn151@naver.com")
                .address(new Address("대전", "수침로", "41"))
                .build();
        //when
        Member saveMember = memberRepository.save(member);
        //then
        assertThat("김건우").isEqualTo(saveMember.getName());
        assertThat(saveMember.getCreatedDate()).isNotNull();
    }

    @Test
    @DisplayName("회원정보 수정 테스트")
    @Rollback(value = false)
    public void updateTest() throws Exception {
        //given
        Optional<Member> findMember = memberRepository.findById("회원아이디1");
        //when
        Member member = findMember.get();
        member.setName("홍길동");
        memberService.update(member);

//        for(int i = 0; i < 1000; i++) {
//            Member member = new Member().builder()
//                    .id("회원아이디"+i)
//                    .name("회원"+i)
//                    .password("0000")
//                    .phoneNum("01022913734")
//                    .email("rjsdn151@naver.com")
//                    .build();
//            Member member1 = addMember(member);
//            Member saveMember = memberRepository.save(member1);
//        }

        //then
        assertThat("홍길동").isEqualTo(member.getName());
    }

    public Member addMember(Member member) {
        Member buildMember = new Member().builder()
                .id(member.getId())
                .name(member.getName())
                .password(member.getPassword())
                .phoneNum(member.getPhoneNum())
                .email(member.getEmail())
                .build();
        return buildMember;
    }
}
