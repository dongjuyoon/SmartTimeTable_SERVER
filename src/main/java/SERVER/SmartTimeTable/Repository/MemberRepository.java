package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;

import java.util.List;

public interface MemberRepository {
    Member findById(String id);
    Member findByPassword(String password);
    void save(Member member);
    List<Member> findAll();
    List<Member> findByStudentId_Email_Name(int studentId, String email, String name);
    List<Member> findByPassword_FindByPassword_Id_StudentID_Name_Email(String id, String email, int studentId,String name);
    void delete(Member member);
}
