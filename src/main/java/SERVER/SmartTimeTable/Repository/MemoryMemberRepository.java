package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {

    MemoryMemberRepository(){};
    private static Map<String, Member> store = new HashMap<>(); // ID를 String으로 저장
    private static long sequence = 0L;



    public Member save(Member member) {
        member.setId(String.valueOf(sequence++)); // Long을 String으로 변환하여 설정
        store.put(member.getID(), member); // String ID를 사용
        return member;
    }

    // ID로 Member를 찾는 메소드 추가
    public Member findById(String id) {
        return store.get(id);
    }

    @Override
    public java.lang.reflect.Member save(java.lang.reflect.Member member) {
        return null;
    }

    @Override
    public Optional<java.lang.reflect.Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<java.lang.reflect.Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<java.lang.reflect.Member> findByPassword(Long password) {
        return Optional.empty();
    }

    @Override
    public Optional<java.lang.reflect.Member> findByMajor(String major) {
        return Optional.empty();
    }

    @Override
    public Optional<java.lang.reflect.Member> findByStudentId(int studentId) {
        return Optional.empty();
    }

    @Override
    public Optional<java.lang.reflect.Member> findByE_mail(Long e_mail) {
        return Optional.empty();
    }

    @Override
    public List<java.lang.reflect.Member> findAll() {
        return List.of();
    }
}
