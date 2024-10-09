package SERVER.SmartTimeTable.Service;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MajorService {
    private final MemberRepository memberRepository;

    public MajorService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public String join(Member member) {
        checkDuplicateId(member.getId()); // ID 중복 체크
        // 회원 저장
        memberRepository.save(member);
        return member.getId();
    }

    private void checkDuplicateId(String id) {
        Optional<Member> existingMember = memberRepository.findById(id);
        existingMember.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        });
    }

    // 전체회원 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String memberId) { // Long으로 수정
        return memberRepository.findById(memberId);
    }
}
