package gwkim.storemanage.api.member.exception;

import gwkim.storemanage.api.member.response.MemberApiResponse;
import gwkim.storemanage.api.member.exception.custom.DormantException;
import gwkim.storemanage.api.member.exception.custom.DuplicatedMemberException;
import gwkim.storemanage.api.member.exception.custom.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"gwkim.storemanage.api.member"})
public class MemberExceptionHandler {
    /**
     * 아이디 비밀번호가 없거나 불일치할 경우 ( 404 : NOT_FOUND )
     * 가장 일반적인 로그인 실패
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<MemberApiResponse<?>> handleMemberNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MemberApiResponse.error(exception.getMessage()));
    }
    /**
     * 휴면계정으로 전환된 경우( 403 : FORBIDDEN )
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(DormantException.class)
    public ResponseEntity<MemberApiResponse<?>> handleDormantException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MemberApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(DuplicatedMemberException.class)
    public ResponseEntity<MemberApiResponse<?>> handleDuplicatedUserException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MemberApiResponse.error(exception.getMessage()));
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<MemberApiResponse<?>> handleValidationExceptions(BindingResult bindingResult) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MemberApiResponse.createFail(bindingResult));
//    }

}
