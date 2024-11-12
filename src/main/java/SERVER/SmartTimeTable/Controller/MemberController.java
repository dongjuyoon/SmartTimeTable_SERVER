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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String userPassword = members.get(0).getPassword();
        return ResponseEntity.ok("비밀번호:"+userPassword);
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
        memberRepository.removeCurrentSubject(member,subjectName); // 과목 삭제
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

        return ResponseEntity.ok(memberRepository.addCourse(member)); // 현재 수강 과목 반환
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
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        if (lectureNumber == null || lectureNumber.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("강의 번호가 제공되지 않았습니다.");
        }

        Subject subject = subjectRepository.findByLectureNumber(lectureNumber);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의를 찾을 수 없습니다.");
        }


        memberRepository.addCurrentSubject(member,subject); // 강의를 멤버의 시간표에 추가
        memberRepository.save(member); // 업데이트된 멤버 객체 저장
        System.out.println(getCurrentSubjects(id));

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
    @PostMapping("{id}/recommendedCommonSubjects")
    public ResponseEntity<List<String>> recommendedCommonSubjects(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> enrolledCommonElectives = member.getCommonElectives();

        // 추천 과목군들
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("성서와인간이해", "현대사회와 기독교 윤리", "종교와과학", "기독교와문화"),
                List.of("글쓰기", "발표와토의"),
                List.of("기초영어", "영어1", "영어2","영어3", "영어4"),
                List.of("영어회화1", "영어회화2","영어회화3", "영어회화4"),
                List.of("4차산업혁명과 미래사회 진로선택", "디지털리터리시의 이해")
        );

        List<String> recommendedSubjects = new ArrayList<>();

        // 1. "성서와인간이해", "현대사회와 기독교 윤리", "종교와과학", "기독교와문화" 리스트 처리
        List<String> subjectGroup1 = allRecommendedSubjects.get(0);
        long subjectGroup1Count = enrolledCommonElectives.stream()
                .filter(subjectGroup1::contains)
                .count();

        if (subjectGroup1Count == 0) {
            recommendedSubjects.addAll(subjectGroup1); // 아무것도 듣지 않았다면 모두 추천
        } else if (subjectGroup1Count == 1) {
            // 1개만 들었으면 그 과목 제외하고 나머지 과목들 추천
            recommendedSubjects.addAll(subjectGroup1.stream()
                    .filter(subject -> !enrolledCommonElectives.contains(subject))
                    .collect(Collectors.toList()));
        }

        // 2. "글쓰기", "발표와토의" 리스트 처리
        List<String> subjectGroup2 = allRecommendedSubjects.get(1);
        if (enrolledCommonElectives.stream().anyMatch(subjectGroup2::contains)) {
            // 하나라도 들었다면 이 리스트는 추천하지 않음
        } else {
            // 둘 다 안 들었으면 추천
            recommendedSubjects.addAll(subjectGroup2);
        }


        boolean hasBasicEnglish = enrolledCommonElectives.contains("기초영어");
        boolean hasEnglish1 = enrolledCommonElectives.contains("영어1");
        boolean hasEnglish2 = enrolledCommonElectives.contains("영어2");
        boolean hasEnglish3 = enrolledCommonElectives.contains("영어3");
        boolean hasEnglish4 = enrolledCommonElectives.contains("영어4");

        if (hasBasicEnglish) {
            recommendedSubjects.add("영어1"); // "기초영어"를 들었으면 "영어1" 추천
        } else if (hasEnglish1) {
            recommendedSubjects.add("영어2"); // "영어1"을 들었으면 "영어2" 추천
        } else if (hasEnglish2) {
            // "영어2"를 수강한 경우 아무것도 추천하지 않음
        } else if (hasEnglish3) {
            recommendedSubjects.add("영어4"); // "영어3"을 들었으면 "영어4" 추천
        } else if (hasEnglish4) {
            // "영어4"를 수강한 경우 아무것도 추천하지 않음
        }



        boolean hasEnglishTalk1 = enrolledCommonElectives.contains("영어회화1");
        boolean hasEnglishTalk2 = enrolledCommonElectives.contains("영어회화2");
        boolean hasEnglishTalk3 = enrolledCommonElectives.contains("영어회화3");
        boolean hasEnglishTalk4 = enrolledCommonElectives.contains("영어회화4");

        if (hasEnglishTalk1) {
            recommendedSubjects.add("영어회화2"); // "기초영어"를 들었으면 "영어1" 추천
        } else if (hasEnglishTalk2) {

        }
        else if (hasEnglishTalk3) {
            recommendedSubjects.add("영어회화4"); // "영어1"을 들었으면 "영어2" 추천
        } else if (hasEnglishTalk4) {

        }
        // 7. "4차산업혁명과 미래사회 진로선택", "디지털리터리시의 이해" 리스트 처리
        List<String> subjectGroup7 = allRecommendedSubjects.get(4);
        if (enrolledCommonElectives.stream().anyMatch(subjectGroup7::contains)) {
            // 하나라도 들었으면 추천하지 않음
        } else {
            recommendedSubjects.addAll(subjectGroup7);
        }

        // 추천 과목이 없으면 NO_CONTENT 상태로 응답
        if (recommendedSubjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // 추천 과목 반환
        return ResponseEntity.ok(recommendedSubjects);
    }


    @PostMapping("{id}/recommendedCoreSubjects")
    public ResponseEntity<List<String>> recommendedCoreSubjects(@PathVariable String id) {
    Member member = memberRepository.findById(id);

    if (member == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    List<String> enrolledCoreElectives = member.getCoreElectives();

    // 추천 과목 목록
    List<List<String>> allRecommendedSubjects = List.of(
            List.of("철학과 인간", "한국근현대사의 이해", "역사와 문명", "4차산업혁명을위한비판적사고", "디지털 콘텐츠로 만나는 한국의 문화유산"),
            List.of("세계화와 사회변화", "민주주의와 현대사회", "창업입문", "여성·소수자·공동체", "현대사회와 심리학", "직무수행과 전략적 의사소통"),
            List.of("글로벌문화", "고전으로읽는 인문학", "예술과창조성", "4차산업혁명시대의예술", "문화리터러시와창의적스토리텔링", "디지털문화의이해"),
            List.of("환경과 인간", "우주,생명,마음", "SW프로그래밍입문", "인공지능의 세계", "4차산업혁명의 이해", "파이썬을활용한데이터분석과인공지능")
    );

    // 추천 과목 리스트 초기화
    List<String> recommendedSubjects = new ArrayList<>();

    // 수강했던 과목이 포함된 추천 과목 리스트를 제외
    for (List<String> subjectList : allRecommendedSubjects) {
        if (enrolledCoreElectives.stream().noneMatch(subjectList::contains)) {
            recommendedSubjects.addAll(subjectList);
        }
    }

    // 추천 과목이 없으면 NO_CONTENT 상태 반환
    if (recommendedSubjects.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // 추천 과목 반환
    return ResponseEntity.ok(recommendedSubjects);
}



}




