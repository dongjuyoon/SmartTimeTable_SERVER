package SERVER.SmartTimeTable.Service;

import SERVER.SmartTimeTable.Domain.Member;

public class MajorService {

    private final MemberRepositroy memberRepository;

    public MajorService(MemberRepositroy memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 중복체크 구현 x
    public Long Registor (Member member){
        memberRepository.Join(member);
        return member.getID();
}
