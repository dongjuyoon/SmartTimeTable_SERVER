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
    public Member findByPassword(String password){
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

        return matchingMembers; // 조건에 맞는 멤버 리스트 반환
    }
    public List<Member> findById_Email_EmailVerified(String id, String email, boolean emailVerified){
        List<Member> matchingMembers_1 = new ArrayList<>();
        for (Member member : memberMap.values()) {
            if (member.getId().equals(id) && // String 비교 시 equals 사용
                    member.getEmail().equals(email) &&
                    member.isEmailVerified() == emailVerified) { // boolean 비교
                matchingMembers_1.add(member);
            }
        }return matchingMembers_1; // 조건에 맞는 멤버 리스트 반환

    }


<<<<<<< HEAD
}
=======
}
>>>>>>> 264117b7ec232c6a2b574203c7747b2792da982f
