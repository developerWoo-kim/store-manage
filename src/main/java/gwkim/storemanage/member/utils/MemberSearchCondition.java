package gwkim.storemanage.member.utils;

import gwkim.storemanage.common.utils.SearchCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchCondition extends SearchCondition {
    private String id;
    private String name;
    private String city;
    private String phoneNum;
}
