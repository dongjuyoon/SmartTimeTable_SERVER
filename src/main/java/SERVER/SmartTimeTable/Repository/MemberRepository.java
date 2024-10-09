package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByPassword(String password); // 비밀번호 타입 String으로 수정

    Optional<Member> findByMajor(String major);

    Optional<Member> findByStudentId(int studentId);

    Optional<Member> findByEmail(String email); // 이메일 타입 String으로 수정

    List<Member> findAll();
}
