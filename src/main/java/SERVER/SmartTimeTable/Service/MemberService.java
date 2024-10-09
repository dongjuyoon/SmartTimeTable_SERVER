package SERVER.SmartTimeTable.Service;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Repository.MemberRepositroy;

public class MemberService {

    private final MemberRepositroy memberRepository;

    public MemberService(MemberRepositroy memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 중복체크 구현 x
    public Long Registor (Member member){
        memberRepository.Join(member);
        return member.getID();
}
