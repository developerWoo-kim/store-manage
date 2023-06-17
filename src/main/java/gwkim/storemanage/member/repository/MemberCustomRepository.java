package gwkim.storemanage.member.repository;

import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberCustomRepository {
    Page<Member> findBySearchOption(Pageable pageable, MemberSearchCondition condition);

    Optional<Member> login(String id, String password);
}
