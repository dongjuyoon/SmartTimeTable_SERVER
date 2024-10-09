package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Service.MajorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MemberController {
    private final MajorService memberService;

    public  MemberController(MajorService memberservice){
        this.memberService = memberservice;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Member member) {
        // 회원가입 로직
        Long memberId = memberService.Registor(member);
        return ResponseEntity.ok("회원가입이 완료되었습니다. ID: " + memberId);
    }
}
