package gwkim.storemanage.api.member.dto;

import gwkim.storemanage.common.domain.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberSignUpDto {
    @NotEmpty(message = "회원 아이디를 입력해주세요.")
    private String id;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty(message = "전화번호를 입력해주세요.")
    private String phoneNum;
    private String email;
    private Address address;
}
