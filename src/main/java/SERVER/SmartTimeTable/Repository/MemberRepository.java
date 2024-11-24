package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberRepository {
    Member findById(String id);
    void save(Member member);
    List<Member> findAll();

    List<Member> findByStudentId_Email_Name(String studentId, String email, String name);

    List<Member> findByPassword_FindByPassword_Id_StudentID_Name_Email(String id, String email, String studentId, String name);

    void delete(Member member);

    void addCurrentSubject(Member member, Subject subject);

    List<String> addCourse(Member member);

    void removeCurrentSubject(Member member,String subjectName);
}
