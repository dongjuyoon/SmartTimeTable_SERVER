package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
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
        member.setMajors(List.of(major));
        member.setStudentId(studentId);
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body("학과 및 학번 정보 저장 완료");
    }
    //핵심교양 정보를 저장하는 api
    @PostMapping("/sign_major")
    public ResponseEntity<String> signUpWithCoreElectives(@RequestParam String id, @RequestParam List<String> coreElectives) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setMajors(coreElectives); // 전공을 리스트로 저장
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("공통교양 완료 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
    }

    //공통교양 정보를 저장하는 api
    @PostMapping("/sign_major")
    public ResponseEntity<String> signUpWithMajorCommonElectives(@RequestParam String id, @RequestParam List<String> commonElectives) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setMajors(commonElectives); // 전공을 리스트로 저장
            memberRepository.save(member); // 메모리 내에 저장
            return ResponseEntity.status(HttpStatus.CREATED).body("전공 이수 완료 정보 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
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
            memberRepository.save(member);
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

    // 로그인하는 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String id, @RequestParam String password) {
        try {
            Member loginMember = memberRepository.findById(id);
            if (loginMember == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 맞지 않습니다.");
            } else if (!loginMember.getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 맞지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 발생");
        }
        return ResponseEntity.ok("로그인 성공");
    }


    // 학번, 이메일, 이름으로 아이디 조회 API
    @GetMapping("/findId")
    public ResponseEntity<String> findId(@RequestParam int studentId,
                                         @RequestParam String email,
                                         @RequestParam String name) {
        List<Member> members = memberRepository.findByStudentId_Email_Name(studentId, email, name);

        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userId = members.get(0).getId();
        return ResponseEntity.ok("아이디: " + userId);
    }

    // 아이디, 이메일, 이메일 인증 여부로 비밀번호 찾는 API
    @GetMapping("/find_password")
    public ResponseEntity<String> findPassword(@RequestParam String id,
                                               @RequestParam String email,
                                               @RequestParam int studentId,
                                               @RequestParam String name){
    List<Member> members = memberRepository.findByPassword_FindByPassword_Id_StudentID_Name_Email(id, email, studentId,name);

        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userPwd = members.get(0).getPassword();
        return ResponseEntity.ok("비밀번호: " + userPwd);
    }
    // 현재 수강 과목 조회 API
    @GetMapping("/{id}/current-subjects")
    public ResponseEntity<List<Subject>> getCurrentSubjects(@PathVariable String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(member.getCurrentSubject());
    }


}
