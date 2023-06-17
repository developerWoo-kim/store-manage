package gwkim.storemanage.member.utils;

import gwkim.storemanage.member.controller.form.MemberForm;
import gwkim.storemanage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberSignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberForm form = (MemberForm) target;
        if(memberRepository.findById(form.getId()).isPresent()){
            errors.rejectValue("id", "invalid.id",
                    new Object[]{form.getId()}, "이미 사용중인 아이디 입니다.");
        }
    }
}
