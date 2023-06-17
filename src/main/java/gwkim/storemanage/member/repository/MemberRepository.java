package gwkim.storemanage.member.repository;

import gwkim.storemanage.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>, MemberCustomRepository {
}
