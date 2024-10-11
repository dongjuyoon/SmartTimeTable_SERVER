package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;

import java.util.List;

public interface MemberRepository {
    Member findById(String id);
    void save(Member member);
    List<Member> findAll();

}
