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
public class MemberApiResponse<T> {
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    protected MemberApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MemberApiResponse<T> loginSuccess(T data) {
        return new MemberApiResponse<>(SUCCESS_STATUS, data, "로그인되었습니다.");
    }

    public static MemberApiResponse<?> error(String message) {
        return new MemberApiResponse<>(ERROR_STATUS, null, message);
    }

    public static <T> MemberApiResponse<T> createSuccess(T data) {
        return new MemberApiResponse<>(SUCCESS_STATUS, data, "회원가입이 완료되었습니다.");
    }
}
