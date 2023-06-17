package gwkim.storemanage.api.member.exception.custom;

public class DormantException extends RuntimeException {
    public DormantException(String message) {
        super(message);
    }
}
