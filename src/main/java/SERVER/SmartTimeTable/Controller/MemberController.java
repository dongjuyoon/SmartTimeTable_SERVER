package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // 기본 URL 매핑
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 학과 및 학번 정보를 저장하는 API
    @PostMapping("/sign")
    public ResponseEntity<String> signUpWithDepartment(@RequestParam String id, @RequestParam String major, @RequestParam int studentId) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            member = new Member();
            member.setId(id);
        }
        member.setMajor(major); // 전공 저장
        member.setStudentId(studentId); // int 타입으로 수정
        memberRepository.save(member); // 메모리 내에 저장
        return ResponseEntity.status(HttpStatus.CREATED).body("학과 및 학번 정보 저장 완료");
    }

    // 아이디와 비밀번호를 저장하는 API
    @PostMapping("/sign_IDPW")
    public ResponseEntity<String> signUpWithIdPassword(@RequestParam String id, @RequestParam String password) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setPassword(password);
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("아이디 및 비밀번호 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
    }

    // 전공 이수 완료 정보를 저장하는 API
    @PostMapping("/sign_major")
    public ResponseEntity<String> signUpWithMajorCourses(@RequestParam String id, @RequestParam List<String> majors) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setMajors(majors); // 전공을 리스트로 저장
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("전공 이수 완료 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
    }

    // 교양 이수 완료 정보를 저장하는 API
    @PostMapping("/sign_elective")
    public ResponseEntity<String> signUpWithElectiveCourses(@RequestParam String id, @RequestParam List<String> electiveCourses) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setElectiveCourses(electiveCourses); // 교양 과목을 리스트로 저장
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("교양 이수 완료 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
    }

    // 회원가입 완료 후 객체 반환 API
    @GetMapping("/complete")
    public ResponseEntity<Member> completeSignUp(@RequestParam String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(member);
    }

    // 모든 사용자 목록 조회 API
    @GetMapping
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }
}
