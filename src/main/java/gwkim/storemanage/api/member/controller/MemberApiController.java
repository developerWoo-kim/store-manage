package gwkim.storemanage.api.member.controller;

import gwkim.storemanage.api.member.response.MemberApiResponse;
import gwkim.storemanage.api.member.dto.MemberLoginDto;
import gwkim.storemanage.api.member.dto.MemberResponseDto;
import gwkim.storemanage.api.member.dto.MemberSignUpDto;
import gwkim.storemanage.api.member.service.MemberApiService;
import gwkim.storemanage.config.jwt.JwtUtils;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.repository.MemberRepository;
import gwkim.storemanage.member.service.MemberService;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final MemberApiService memberApiService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<MemberApiResponse<MemberResponseDto>> login(@RequestBody MemberLoginDto memberLoginDto) {
        return memberApiService.login(memberLoginDto);
    }

    /**
     * 등록 V1: 요청 값으로 Member 엔티티를 직접 받는다.
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 엔티티에 API 검증을 위한 로직이 들어간다. (@NotEmpty 등등)
     * - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를
     위한 모든 요청 요구사항을 담기는 어렵다.
     * - 엔티티가 변경되면 API 스펙이 변한다.
     * 결론
     * - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     *
     * 
     *
     *
     *
     */
    @PostMapping("/api/v1/members")
    public MemberApiResponse<String> saveMemberV2(@RequestBody @Valid MemberSignUpDto memberDto) {

        Member member = new Member().builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .phoneNum(memberDto.getPhoneNum())
                .build();

        String saveId = memberService.save(member);

        return MemberApiResponse.createSuccess(saveId);

    }

    @GetMapping("/api/v1/members")
    public Page<Member> memberList(Pageable pageable, MemberSearchCondition memberSearchCondition) {

        return memberService.findBySearchOption(pageable, memberSearchCondition);
//        return memberRepository.findById(id)
//                .stream()
//                .collect(Collectors.toList());
    }
}
