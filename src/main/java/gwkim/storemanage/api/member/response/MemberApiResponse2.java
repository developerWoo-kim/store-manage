package gwkim.storemanage.api.member.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MemberApiResponse2<T> {
    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    private MemberApiResponse2(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MemberApiResponse2<T> loginSuccess(T data) {
        return new MemberApiResponse2<>(SUCCESS_STATUS, data, "로그인되었습니다.");
    }

    public static MemberApiResponse2<?> fail(String message) {
        return new MemberApiResponse2<>(FAIL_STATUS, null, message);
    }

    // 에러 발생으로 API 호출 실패시 반환
    public static MemberApiResponse2<?> error(String message) {
        return new MemberApiResponse2<>(ERROR_STATUS, null, message);
    }

    public static <T> MemberApiResponse2<T> createSuccess(T data) {
        return new MemberApiResponse2<>(SUCCESS_STATUS, data, "회원가입이 완료되었습니다.");
    }

    public static MemberApiResponse2<?> createSuccessWithNoContent() {
        return new MemberApiResponse2<>(SUCCESS_STATUS, null, null);
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static MemberApiResponse2<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new MemberApiResponse2<>(FAIL_STATUS, errors, "데이터가 유효하지 않습니다.");
    }
}
