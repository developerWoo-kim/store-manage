package gwkim.storemanage.member.controller;

import gwkim.storemanage.common.module.PagingDto;
import gwkim.storemanage.config.jwt.JwtUtils;
import gwkim.storemanage.member.controller.form.MemberForm;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.repository.MemberRepository;
import gwkim.storemanage.member.service.MemberService;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import gwkim.storemanage.member.utils.MemberSignUpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final MemberSignUpValidator memberSignUpValidator;

    @GetMapping("/signin")
    public String signinForm(Model model) {
        return "members/auth-signin";
    }

    @PostMapping("/signin")
    public String signin(@RequestParam("id") String id, @RequestParam("password") String password) {
        Member member = memberRepository.findById(id).get();
        return jwtUtils.createToken(member.getId(), member.getName());
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/auth-signup";
    }
    @PostMapping("/signup")
    public String signup(@Valid MemberForm form, Errors errors) {
        if(errors.hasErrors()) {
            return "members/auth-signup";
        }
        memberSignUpValidator.validate(form, errors);
        if(errors.hasErrors()) {
            return "members/auth-signup";
        }

        memberService.save(new Member().builder()
                .id(form.getId())
                .password(form.getPassword())
                .name(form.getName())
                .email(form.getEmail())
                .phoneNum(form.getPhoneNum())
                .build());

        return "members/auth-signup";
    }

    @GetMapping("/members")
    public String list(Model model, @ModelAttribute("memberSearchCondition") MemberSearchCondition condition, Pageable pageable) {
        Page<Member> page = memberService.findBySearchOption(pageable, condition);
        PagingDto<Member> memberPagingDto = new PagingDto<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
        model.addAttribute("pagination", memberPagingDto);
        model.addAttribute("content", "members/memberList");
        return "subLayout";
    }
}
