package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import SERVER.SmartTimeTable.Repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "*") // CORS 설정
public class MemberController {


    private MemberRepository memberRepository;
    private SubjectRepository subjectRepository;


    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
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
    public ResponseEntity<String> signUpWithDepartment(HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> params;
        try {
            params = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식");
        }

        // JSON에서 필드 가져오기
        String id = params.get("id");
        String major = params.get("major");
        String studentId = params.get("student_id");
        String semester = params.get("semester");
        String grade = params.get("grade");
        try {
            // Member 객체 생성 및 설정
            Member member = memberRepository.findById(id);
            if (member == null) {
                member = new Member();
                member.setId(id);
            }
            member.setMajor(major);
            member.setStudentId(studentId);
            member.setSemester(semester);
            member.setGrade(grade);
            memberRepository.save(member);  // 변경 사항 저장

            return ResponseEntity.ok("아이디, 학과, 학번 성공");
        } catch (Exception e) {
            e.printStackTrace();  // 예외 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }
    }

    //회원가입 두번째
    @PostMapping("{id}/sign_IDPW")
    public ResponseEntity<String> signUpWithIdPassword(
            @PathVariable("id") String id, // 매개변수 이름을 명시적으로 지정
            HttpServletRequest request) {

        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> params;
        try {
            params = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식");
        }


        // JSON에서 필드 가져오기
        String password = params.get("password");
        String email = params.get("email");
        String name = params.get("name");

        // 유효성 검사
        if (password == null || email == null || name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모든 필드를 입력해야 합니다.");
        }
        try{
            Member optionalMember = memberRepository.findById(id);

            // 기존 회원 정보 업데이트
            optionalMember.setEmail(email);
            optionalMember.setName(name);
            optionalMember.setPassword(password);

            // 회원 정보 저장
            memberRepository.save(optionalMember);
            return ResponseEntity.ok("회원정보 업데이트 성공");

        } catch (Exception e) {
            e.printStackTrace(); // 로그에 오류를 남김
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    //전공 체크
    @PostMapping("{id}/sign_majorCourses")
    public ResponseEntity<String> signUpWithMajorCourses(
            @PathVariable("id") String id,
            HttpServletRequest request) {

        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params;
        try {
            params = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식");
        }

        // 전공 과목 배열 처리
        List<String> majors = (List<String>) params.get("majors");

        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않습니다.");
            }
            member.setMajors(majors);
            memberRepository.save(member); // 변경 사항 저장
            return ResponseEntity.ok("전공 정보 저장");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }

    //교양 체크
    @PostMapping("{id}/sign_elective")
    public ResponseEntity<String> signUpWithElectiveCourses(
            @PathVariable("id") String id, // 매개변수 이름을 명시적으로 지정
            @RequestBody Map<String, List<String>> requestBody) { // Map으로 JSON 요청 처리
        try {
            Member member = memberRepository.findById(id);

            // JSON에서 교양 과목 리스트 가져오기
            List<String> coreElectives = requestBody.get("coreElectives");
            List<String> commonElectives = requestBody.get("commonElectives");
            member.setCoreElectives(coreElectives);
            member.setCommonElectives(commonElectives);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            HttpServletRequest request) {

        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류: " + e.getMessage());
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params;
        try {
            params = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식");
        }

        String id = (String) params.get("id");
        String password = (String) params.get("password");

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
    public ResponseEntity<String> findId(
            @RequestParam("student_id") String studentId,
            @RequestParam("email") String email,
            @RequestParam("name") String name) {

        List<Member> members = memberRepository.findByStudentId_Email_Name(studentId, email, name);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userId = members.get(0).getId();
        return ResponseEntity.ok("아이디: " + userId);
    }

    //비번찾기
    @GetMapping("/findPassword")
    public ResponseEntity<String> findPassword(
            @RequestParam("id") String id,
            @RequestParam("student_id") String studentId,
            @RequestParam("email") String email,
            @RequestParam("name") String name) {

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
    public ResponseEntity<List<Subject>> deleteCurrentSubject(@PathVariable String id,
                                                              HttpServletRequest request) {

        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params;
        try {
            params = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.badRequest().body(null);
        }

        // 과목 이름을 List<String>으로 처리
        String subjectNames = (String) params.get("subjectName");

        if (subjectNames == null || subjectNames.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 과목 삭제 메소드 호출
        memberRepository.removeCurrentSubject(member, subjectNames); // 과목 삭제
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

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

    //수강했던 전공 과목 조회
    @GetMapping("/{id}/completedCourseHistoryManagementMajor")
    public ResponseEntity<List<String>> completedCourseHistoryManagementMajor(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(memberRepository.getCurrentMajors(member)); // 현재 수강 과목 반환
    }

    // 수강했던 핵심 교양 과목 조회
    @GetMapping("/{id}/completedCourseHistoryManagementCore")
    public ResponseEntity<List<String>> completedCourseHistoryManagementCore(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(memberRepository.getCurrentCoreElectives(member)+memberRepository.getCurrentCommonElectives(member)); // 현재 수강 과목 반환
    }

    // 수강했던 공통 교양 과목 조회
    @GetMapping("/{id}/completedCourseHistoryManagementCommon")
    public ResponseEntity<List<String>> completedCourseHistoryManagementCommon(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(memberRepository.getCurrentCommonElectives(member)); // 현재 수강 과목 반환
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
    public ResponseEntity<String> addSubjectToMember(@PathVariable String id, HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();

        // 요청 본문 읽기
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBody;
        try {
            requestBody = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.badRequest().body("잘못된 JSON 형식입니다.");
        }

        // JSON에서 lectureNumber 추출
        String lectureNumber = requestBody.get("lectureNumber");

        // 회원 및 과목 조회
        Member member = memberRepository.findById(id);
        Subject subject = subjectRepository.findByLectureNumber(lectureNumber);

        // 회원이 존재하지 않는 경우 처리
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않습니다.");
        }

        // 과목이 존재하지 않는 경우 처리
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 강의가 존재하지 않습니다.");
        }

        // 강의를 멤버의 시간표에 추가
        memberRepository.addCurrentSubject(member, subject);
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("강의가 시간표에 추가되었습니다.");
    }


    //멤버 정보(학과 학번 이메일) myPage
    @GetMapping("/{id}/myPage")
    public ResponseEntity<Map<String, String>> myPage(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "해당 사용자를 찾을 수 없습니다."));
        }

        Map<String, String> memberInfo = new HashMap<>();
        memberInfo.put("name", member.getName());
        memberInfo.put("major", member.getMajor());
        memberInfo.put("id", member.getId());
        memberInfo.put("std_id", member.getStudentId());
        memberInfo.put("email", member.getEmail());
        memberInfo.put("grade", member.getGrade());
        memberInfo.put("semester", member.getSemester());

        return ResponseEntity.ok(memberInfo); // 멤버정보 반환
    }

    //학년 학기 수정
    @PostMapping("{id}/manageGradeSemester")
    public ResponseEntity<String> completeSemesterGradeSave(@PathVariable String id, HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();

        // 요청 본문 읽기
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBody;
        try {
            requestBody = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식입니다.");
        }

        // JSON에서 학기 및 성적 정보 추출
        String semester = requestBody.get("semester");
        String grade = requestBody.get("grade");

        if (semester == null || grade == null) {
            return ResponseEntity.badRequest().body("학기 또는 성적 정보가 누락되었습니다.");
        }

        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        // 과목 설정
        member.setSemester(semester);
        member.setGrade(grade);

        // 변경사항 저장
        memberRepository.save(member);

        return ResponseEntity.ok("학기 및 성적 정보가 성공적으로 저장되었습니다.");
    }


    //이수 교양과목 내역 관리 수정
    @PostMapping("{id}/manageCompletedElectives")
    public ResponseEntity<String> completedCultureCourseSave(@PathVariable String id, HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();

        // 요청 본문 읽기
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> requestBody;
        try {
            requestBody = objectMapper.readValue(json, new TypeReference<Map<String, List<String>>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.badRequest().body("잘못된 JSON 형식입니다.");
        }

        // JSON에서 전공, 전공선택, 공통선택 과목 추출
        List<String> coreElectives = requestBody.get("coreElectives");
        List<String> commonElectives = requestBody.get("commonElectives");

        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        // 과목 설정
        member.setCoreElectives(coreElectives);
        member.setCommonElectives(commonElectives);

        // 저장 메서드 호출
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("교양 과목 정보가 성공적으로 저장되었습니다.");
    }

    //이수 전공과목 내역 관리 수정
    @PostMapping("{id}/manageCompletedMajors")
    public ResponseEntity<String> completedMajorSave(@PathVariable String id, HttpServletRequest request) {
        StringBuilder jsonBuilder = new StringBuilder();

        // 요청 본문 읽기
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        String json = jsonBuilder.toString();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> requestBody;
        try {
            requestBody = objectMapper.readValue(json, new TypeReference<Map<String, List<String>>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // 예외 로그 추가
            return ResponseEntity.badRequest().body("잘못된 JSON 형식입니다.");
        }

        // JSON에서 전공, 전공선택, 공통선택 과목 추출
        List<String> majors = requestBody.get("majors");

        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        // 과목 설정
        member.setMajors(majors);

        // 저장 메서드 호출
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("과목 정보가 성공적으로 저장되었습니다.");
    }

    @PostMapping("{id}/recommendedMajorSubjects")
    public ResponseEntity<Map<String, List<Subject>>> recommendedMajorSubjects(
            @PathVariable String id, @RequestBody Map<String, Object> requestBody) {

        // 요청 본문에서 전공, 학년, 학기 추출
        List<String> majors = (List<String>) requestBody.get("majors");
        String grade = (String) requestBody.get("grade"); // 학년
        String semester = (String) requestBody.get("semester"); // 학기

        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> enrolledMajorElectives = member.getMajors();
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();

        // 과목과 선이수 과목 정의
        Map<String, List<String>> coursePrerequisites = subjectRepository.getCoursePrerequisites();

        // 추천 과목 로직
        for (Map.Entry<String, List<String>> entry : coursePrerequisites.entrySet()) {
            String subject = entry.getKey();
            List<String> prerequisites = entry.getValue();

            // 선이수 과목 추천 로직
            subjectRepository.recommendPrerequisites(enrolledMajorElectives, recommendedSubjectsMap, prerequisites);
            // 학년 및 학기에 따른 추천 과목 추가
            subjectRepository.recommendSubjectsByGradeAndSemester(grade, semester, subject, enrolledMajorElectives, recommendedSubjectsMap);
        }

        // 추천 과목이 없으면 빈 리스트로 반환
        if (recommendedSubjectsMap.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyMap());
        }

        // 추천 과목 반환
        return ResponseEntity.ok(recommendedSubjectsMap);
    }



    @PostMapping("{id}/recommendedCoreSubjects")
    public ResponseEntity<Map<String, List<Subject>>> recommendedCoreSubjects(@PathVariable String id) {
        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // SubjectRepository에서 추천 과목 조회
        Map<String, List<Subject>> recommendedSubjects = subjectRepository.findRecommendedCoreSubjects(member);

        // 추천 과목이 없으면 NO_CONTENT 상태 반환
        if (recommendedSubjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // 추천 과목 반환
        return ResponseEntity.ok(recommendedSubjects);
    }

    @PostMapping("{id}/recommendedCommonSubjects")
    public ResponseEntity<List<String>> recommendedCommonSubjects(@PathVariable String id) {
        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // SubjectRepository에서 추천 과목 조회
        List<String> recommendedSubjects = subjectRepository.findRecommendedCommonSubjects(member);

        // 추천 과목이 없으면 NO_CONTENT 상태로 응답
        if (recommendedSubjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // 추천 과목 반환
        return ResponseEntity.ok(recommendedSubjects);
    }



}

