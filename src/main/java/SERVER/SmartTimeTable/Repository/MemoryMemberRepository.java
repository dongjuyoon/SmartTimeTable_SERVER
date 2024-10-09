package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<String, Member> store = new HashMap<>(); // ID를 String으로 사용
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(String.valueOf(++sequence)); // Long을 String으로 변환하여 ID 설정
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) { // ID 타입을 String으로 변경
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Member> findByPassword(String password) { // 비밀번호 타입 String으로 수정
        return store.values().stream()
                .filter(member -> member.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public Optional<Member> findByMajor(String major) {
        return store.values().stream()
                .filter(member -> member.getMajor().equals(major))
                .findFirst();
    }

    @Override
    public Optional<Member> findByStudentId(int studentId) {
        return store.values().stream()
                .filter(member -> member.getSTUDENT_ID() == studentId)
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByEmail(String email) { // 이메일 타입 String으로 수정
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }
}
