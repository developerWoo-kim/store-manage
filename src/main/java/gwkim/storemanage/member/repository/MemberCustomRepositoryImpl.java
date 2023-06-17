package gwkim.storemanage.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gwkim.storemanage.common.module.PagingModule;
import gwkim.storemanage.config.auditing.QBaseEntity;
import gwkim.storemanage.member.domain.Member;
import gwkim.storemanage.member.domain.QMember;
import gwkim.storemanage.member.utils.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

import static gwkim.storemanage.member.domain.QMember.*;

public class MemberCustomRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    public MemberCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Member> findBySearchOption(Pageable pageable, MemberSearchCondition condition) {
        List<Member> contents = getMemberList(pageable, condition);
        Long count = getCount(condition);
        return new PageImpl<>(contents, pageable, count);
    }

    @Override
    public Optional<Member> login(String id, String password) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(eqId(id).and(eqPassword(password)))
                .fetchOne());
    }

    private List<Member> getMemberList(Pageable pageable, MemberSearchCondition condition) {
        return queryFactory
                .selectFrom(member)
                .where(
                        eqName(condition.getName()),
                        eqId(condition.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long getCount(MemberSearchCondition condition) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(
                        eqName(condition.getName()),
                        eqId(condition.getId())
                )
                .fetchOne();
    }

    private BooleanExpression eqName(String name) {
        if(name == null || name.isEmpty()) {
            return null;
        }
        return member.name.contains(name);
    }

    private BooleanExpression eqId(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        }
        return member.id.eq(id);
    }

    private BooleanExpression eqPassword(String password) {
        if(password == null || password.isEmpty()) {
            return null;
        }
        return member.password.eq(password);
    }
}
