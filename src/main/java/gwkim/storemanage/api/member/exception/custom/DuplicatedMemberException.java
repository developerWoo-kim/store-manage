package gwkim.storemanage.api.member.exception.custom;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException(String message) {
        super(message);
    }
}
