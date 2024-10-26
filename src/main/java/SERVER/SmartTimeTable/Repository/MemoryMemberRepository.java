package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private final Map<String, Member> memberMap = new HashMap<>();

    @Override
    public Member findById(String id) {
        return memberMap.get(id);
    }

    @Override
    public Member findByPassword(String password) {
        return memberMap.get(password);
    }

    @Override
    public void save(Member member) {
        memberMap.put(member.getId(), member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public List<Member> findByStudentId_Email_Name(int studentId, String email, String name) {
        List<Member> matchingMembers = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getStudentId() == studentId &&
                    member.getEmail().equals(email) &&
                    member.getName().equals(name)) {
                matchingMembers.add(member);
            }
        }
        return matchingMembers;
    }

    @Override
    public List<Member> findByPassword_Email_EmailVerified(String id, String email, int studentId) {
        List<Member> matchingMembers = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getId().equals(id) &&
                    member.getEmail().equals(email) &&
                    member.getStudentId() == studentId) {
                matchingMembers.add(member);
            }
        }
        return matchingMembers;
    }
}