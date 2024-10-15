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
        member.setMajors(List.of(major)); // 전공을 리스트로 저장
        member.setStudentId(studentId); // 학번 저장
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
    public ResponseEntity<String> signUpWithElectiveCourses(@RequestParam String id, @RequestParam List<String> coreElectives, @RequestParam List<String> commonElectives) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setCoreElectives(coreElectives); // 핵심 교양 과목을 리스트로 저장
            member.setCommonElectives(commonElectives); // 공통 교양 과목을 리스트로 저장
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("교양 이수 완료 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
    }

    // 이메일 인증 요청 API
    @PostMapping("/verify_email")
    public ResponseEntity<String> verifyEmail(@RequestParam String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자가 존재하지 않음");
        }

        // 이메일 인증 로직 (예: 인증 이메일 전송)
        // 이곳에 실제 이메일 전송 코드를 추가합니다.

        return ResponseEntity.ok("인증 이메일이 전송되었습니다.");
    }

    // 이메일 인증 확인 API
    @PostMapping("/confirm_email")
    public ResponseEntity<String> confirmEmail(@RequestParam String id, @RequestParam String verificationCode) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자가 존재하지 않음");
        }

        // 인증 코드 검증 로직 (예: 저장된 코드와 비교)
        // 여기서 실제 인증 코드 검증 로직을 추가합니다.

        member.setEmailVerified(true); // 이메일 인증 완료
        memberRepository.save(member); // 메모리 내에 저장

        return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
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
    // 로그인하는 api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String id, @RequestParam String password) {
        Member loginMember = memberRepository.findById(id);
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 맞지 않습니다.");
        } else if (!loginMember.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 맞지 않습니다.");
        }
        return ResponseEntity.ok("로그인 성공"); // 성공시 성공 메시지 반환
    }

    // 학번, 이메일, 이름으로 아이디 조회 API
    @GetMapping("find-id")
    public ResponseEntity<String> findId(@RequestParam int studentId,
                                         @RequestParam String email,
                                         @RequestParam String name) {
        List<Member> members = memberRepository.findByStudentId_Email_Name(studentId, email, name);

        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userId = members.get(0).getId(); // 첫 번째 멤버의 아이디 반환
        return ResponseEntity.ok("아이디: " + userId);
    }

    // 아이디, 이메일, 이메일 인증 여부로 비밀번호 찾는 API
    @GetMapping("find-password") // 메서드 이름 변경
    public ResponseEntity<String> findPassword(@RequestParam String id,
                                               @RequestParam String email,
                                               @RequestParam Boolean emailVerified) {
        List<Member> members = memberRepository.findById_Email_EmailVerified(id, email, emailVerified);

        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userPwd = members.get(0).getPassword(); // 첫 번째 멤버의 비밀번호 반환
        return ResponseEntity.ok("비밀번호: " + userPwd);
    }
}