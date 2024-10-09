package SERVER.SmartTimeTable.Repository;
// 올바른 Member 임포트
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByPassword(Long password); // 비밀번호 타입 Long 수정

    Optional<Member> findByMajor(String major); // 전공을 찾는 메서드 이름 수정

    Optional<Member> findByStudentId(int studentId); // 학생 ID를 찾는 메서드 이름 수정

    Optional<Member> findByE_mail(Long e_mail);

    List<Member> findAll();
}
