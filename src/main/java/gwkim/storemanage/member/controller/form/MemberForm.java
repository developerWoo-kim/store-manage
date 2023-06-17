package gwkim.storemanage.member.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 아이디를 입력해주세요.")
    private String id;
    private String password;
    private String name;
    private String email;
    private String phoneNum;
}
