package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepositroy {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member Join(Member member){
        member.setId(sequence++);
        store.put(member.getID(), member);
        return member;

    }

}
