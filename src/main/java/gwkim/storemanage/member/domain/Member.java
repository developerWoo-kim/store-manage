package gwkim.storemanage.member.domain;

import gwkim.storemanage.common.domain.Address;
import gwkim.storemanage.config.auditing.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    private String id;
    private String name;
    private String password;
    private String phoneNum;
    private String email;
    private String status;
    @Embedded
    private Address address;

    @Builder
    public Member(String id, String name, String password, String phoneNum, String email, Address address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }
}
