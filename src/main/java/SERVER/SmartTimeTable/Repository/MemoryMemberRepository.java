package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final Map<String, Member> memberMap;
    // 생성자에서 초기화
    public MemoryMemberRepository() {
        this.memberMap = new HashMap<>(); // 여기서 초기화
    }

    @Override
    public Member findById(String id) {
        return memberMap.get(id);
    }

    @Override
    public void save(Member member) {
        System.out.println("Saving member: " + member.getId() + ", " + member.getEmail() + ", " + member.getStudentId() + ", " + member.getName() + member.getCommonElectives()+member.getCoreElectives()+member.getMajors());
        memberMap.put(member.getId(), member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public List<Member> findByStudentId_Email_Name(String studentId, String email, String name) {
        List<Member> matchingMembers = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getStudentId().equals(studentId)  &&
                    member.getEmail().equals(email) &&
                    member.getName().equals(name)) {
                matchingMembers.add(member);
            }
        }
        return matchingMembers;
    }

    @Override
    public List<Member> findByPassword_FindByPassword_Id_StudentID_Name_Email(String id, String email, String studentId, String name) {
        List<Member> matchingMembers = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getId().equals(id) &&
                    member.getEmail().equals(email) &&
                    member.getStudentId().equals(studentId)  &&
                    member.getName().equals(name)) {
                matchingMembers.add(member);
            }
        }
        return matchingMembers;
    }

    @Override
    public void delete(Member member) {
        memberMap.remove(member.getId());
    }

    @Override
    public void addCurrentSubject(Member member, Subject subject) {
        member.getCurrentSubject().add(subject);
    }
    @Override
    public List<String> getCurrentMajors(Member member) {
        return member.getMajors();
    }
    @Override
    public List<String> getCurrentCoreElectives(Member member) {
        return member.getCoreElectives();
    }
    @Override
    public List<String> getCurrentCommonElectives(Member member) {
        return member.getCommonElectives();
    }



    @Override
    public void removeCurrentSubject(Member member, String subjectName) {
        member.getCurrentSubject().removeIf(subject -> subject.getName().equals(subjectName));
    }


}

