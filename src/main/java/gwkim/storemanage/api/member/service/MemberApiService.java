package gwkim.storemanage.api.member.service;

import gwkim.storemanage.api.member.response.MemberApiResponse;
import gwkim.storemanage.api.member.dto.MemberLoginDto;
import gwkim.storemanage.api.member.dto.MemberResponseDto;
import gwkim.storemanage.api.member.exception.custom.DormantException;
import gwkim.storemanage.api.member.exception.custom.MemberNotFoundException;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberApiService {
    private final MemberRepository memberRepository;

    public ResponseEntity<MemberApiResponse<MemberResponseDto>> login(MemberLoginDto memberLoginDto) {
        Optional<Member> member = memberRepository.login(memberLoginDto.getId(), memberLoginDto.getPassword());

        member.orElseThrow(() -> new MemberNotFoundException("아이디 또는 비밀번호를 잘못 입력했습니다.\n입력하신 내용을 다시 확인해주세요."));

        Member foundMember = member.get();

        Optional.ofNullable(foundMember.getStatus())
                .filter(status -> "A".equals(status))
                .orElseThrow(() -> new DormantException("장기간 미접속하여 휴면계정으로 전환되었습니다."));

        return ResponseEntity.status(HttpStatus.OK)
                .body(MemberApiResponse.loginSuccess(
                        new MemberResponseDto(foundMember.getId(), foundMember.getName()))
                );
    }

    //        return MemberApiResponse.loginSuccess(new MemberResponseDto(foundMember.getId(), foundMember.getName()));
//        return new ResponseEntity<MemberApiResponse<MemberResponseDto>>
//                (MemberApiResponse.loginSuccess(
//                        new MemberResponseDto(foundMember.getId(), foundMember.getName())),null, HttpStatus.OK);
}
