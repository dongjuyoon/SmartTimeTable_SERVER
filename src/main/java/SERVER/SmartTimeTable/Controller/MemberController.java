package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Service.MajorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members") // 기본 URL 매핑
public class MemberController {
    private final MajorService memberService;

    public MemberController(MajorService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody Member member) {
        Long memberId = memberService.join(member);
        return ResponseEntity.ok(memberId); // 성공적으로 ID 반환
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        List<Member> members = memberService.findMember();
        return ResponseEntity.ok(members); // 전체 회원 목록 반환
    }

    // 특정 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> findOne(@PathVariable Long id) {
        return memberService.findOne(id)
                .map(ResponseEntity::ok) // 회원이 존재하면 OK 반환
                .orElse(ResponseEntity.notFound().build()); // 존재하지 않으면 404 반환
    }
}
