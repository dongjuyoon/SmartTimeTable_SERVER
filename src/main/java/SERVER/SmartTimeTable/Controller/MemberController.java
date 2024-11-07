package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
import SERVER.SmartTimeTable.Repository.SubjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "*") // CORS 설정
public class MemberController {

    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository, SubjectRepository subjectRepository) {
        this.memberRepository = memberRepository;
        this.subjectRepository = subjectRepository;
    }

    private ResponseEntity<String> saveMember(Member member) {
        memberRepository.save(member);
        System.out.println("Saving member: " + member.getId() + ", " + member.getEmail() + ", " + member.getStudentId() + ", " + member.getName() + ", " + member.getPassword()+", " + member.getCommonElectives()+", " + member.getCoreElectives()+", " + member.getMajors()+", " + member.getMajor());
        return ResponseEntity.status(HttpStatus.CREATED).body("정보 저장 완료");
    }

    // 아이디 중복 체크 메소드
    @GetMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestParam String id) {
        if (memberRepository.findById(id) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }
    //전공 고르기
    @PostMapping("/sign")
    public ResponseEntity<String> signUpWithDepartment(@RequestParam String id, @RequestParam List<String> major, @RequestParam int studentId) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            member = new Member();
            member.setId(id);
        }
        member.setMajor(major);
        member.setStudentId(studentId);
        return saveMember(member);
    }
    // 회원가입
    @PostMapping("/sign_IDPW")
    public ResponseEntity<String> signUpWithIdPassword(@RequestParam String id, @RequestParam String password, @RequestParam String email, @RequestParam String name) {
        try {
            if (memberRepository.findById(id) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
            }

            Member member = new Member();
            member.setId(id);
            member.setEmail(email);
            member.setName(name);
            member.setPassword(password); // 평문 비밀번호 저장
            return saveMember(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
    //전공 들었는지 확인
    @PostMapping("/sign_majorCourses")
    public ResponseEntity<String> signUpWithMajorCourses(@RequestParam String id, @RequestParam List<String> majors) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setMajors(majors);
            return saveMember(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
    //교양 들었는지 확인
    @PostMapping("/sign_elective")
    public ResponseEntity<String> signUpWithElectiveCourses(@RequestParam String id, @RequestParam List<String> coreElectives, @RequestParam List<String> commonElectives) {
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setCoreElectives(coreElectives);
            member.setCommonElectives(commonElectives);
            return saveMember(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String id, @RequestParam String password) {
        try {
            Member loginMember = memberRepository.findById(id);
            if (loginMember == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 맞지 않습니다.");
            } else if (!loginMember.getPassword().equals(password)) { // 비밀번호 확인
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 맞지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류 발생: " + e.getMessage());
        }
        return ResponseEntity.ok("로그인 성공");
    }
    //아이디찾기
    @GetMapping("/findId")
    public ResponseEntity<String> findId(@RequestParam int studentId, @RequestParam String email, @RequestParam String name) {
        List<Member> members = memberRepository.findByStudentId_Email_Name(studentId, email, name);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userId = members.get(0).getId();
        return ResponseEntity.ok("아이디: " + userId);
    }
    //비번찾기
    @GetMapping("/findPassword")
    public ResponseEntity<String> findPassword(@RequestParam String id, @RequestParam String email, @RequestParam int studentId, @RequestParam String name) {
        List<Member> members = memberRepository.findByPassword_FindByPassword_Id_StudentID_Name_Email(id, email, studentId, name);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
        String userpassword = members.get(0).getPassword();
        return ResponseEntity.ok("비밀번호:"+userpassword);
    }
    //현재 수강중인 과목
    @GetMapping("/{id}/current-subjects")
    public ResponseEntity<List<Subject>> getCurrentSubjects(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Subject> currentSubjects = member.getCurrentSubject();
        return ResponseEntity.ok(currentSubjects); // 현재 수강 과목 반환
    }
    //학생 시간표 삭제
    @DeleteMapping("/{id}/delete-current-subject")
    public ResponseEntity<List<Subject>> deleteCurrentSubject(@PathVariable String id, @RequestParam String subjectName) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 과목 삭제 메소드 호출
        member.removeCurrentSubject(subjectName); // 과목 삭제
        saveMember(member); // 업데이트된 멤버 객체 저장

        // 삭제 후 남아있는 현재 수강 과목 반환
        List<Subject> currentSubjects = member.getCurrentSubject();
        return ResponseEntity.ok(currentSubjects);
    }
    // 회원탈퇴
    @DeleteMapping("/{id}/withdrawalOfMembership")
    public ResponseEntity<String> WithdrawalOfMembership(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않습니다.");
        }

        memberRepository.delete(member); // 회원 탈퇴 처리
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content 응답
    }

    //수강했던 과목 돌려줌
    @GetMapping("/{id}/completedCourseHistoryManagement1")
    public ResponseEntity<List<String>> completedCourseHistoryManagement1(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(member.addCourse()); // 현재 수강 과목 반환
    }

    //공통,핵심 전공 과목 돌려줌
    @GetMapping("/{id}/completedCourseHistoryManagement2")
    public ResponseEntity<List<Subject>> completedCourseHistoryManagement2(@PathVariable String id) {
        Member member = memberRepository.findById(id);


        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Subject> allElectives = subjectRepository.getAllElectives();

        return ResponseEntity.ok(allElectives);
    }

    //특정강의를 멤버에 추가
    @PostMapping("/{id}/addToMember")
    public ResponseEntity<String> addSubjectToMember(@RequestBody String lectureNumber,@PathVariable String id) {
        Member member = memberRepository.findById(id);
        Subject subject = subjectRepository.findByLectureNumber(lectureNumber);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        member.addCurrentSubject(subject); // 강의를 멤버의 시간표에 추가
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("강의가 시간표에 추가되었습니다.");
    }

    //멤버 정보(학과 학번 이메일) myPage
    @GetMapping("/{id}/myPage")
    public ResponseEntity<String> myPage(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
        String addMyPage= member.getEmail()+member.getMajor()+member.getStudentId();

        return ResponseEntity.ok(addMyPage); // 현재 수강 과목 반환
    }

    //이수과목 내역 관리 수정
    @PostMapping("{id}/completedCourseSave")
    public ResponseEntity<String> completedCourseSave(@PathVariable String id,@RequestParam List<String> majors,@RequestParam List<String> coreElectives,@RequestParam List<String> commonElectives) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
        member.setMajors(majors);
        member.setCommonElectives(commonElectives);
        member.setCoreElectives(coreElectives);

        return saveMember(member);
    }





}
