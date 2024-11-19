package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemberRepository;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
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
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



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
    public ResponseEntity<String> findId(HttpServletRequest request) {

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

        String studentId = (String) params.get("student_id");
        String email = (String) params.get("email");
        String name = (String) params.get("name");

        List<Member> members = memberRepository.findByStudentId_Email_Name(studentId, email, name);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }

        String userId = members.get(0).getId();
        return ResponseEntity.ok("아이디: " + userId);
    }

    //비번찾기
    @GetMapping("/findPassword")
    public ResponseEntity<String> findPassword(HttpServletRequest request) {
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
        String studentId = (String) params.get("student_id");
        String email = (String) params.get("email");
        String name = (String) params.get("name");

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
    public ResponseEntity<String> myPage(@PathVariable String id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자를 찾을 수 없습니다.");
        }
        String addMyPage= member.getEmail()+member.getMajor()+member.getStudentId();

        return ResponseEntity.ok(addMyPage); // 현재 수강 과목 반환
    }

    //학기 학년  수정
    @PostMapping("{id}/completeSemesterGradeSave")
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
        Map<String, List<String>> requestBody;
        try {
            requestBody = objectMapper.readValue(json, new TypeReference<Map<String, List<String>>>() {});
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("잘못된 JSON 형식입니다.");
        }

        // JSON에서 전공, 전공선택, 공통선택 과목 추출
        String semester = requestBody.get("semester") != null ? requestBody.get("semester").get(0) : null;
        String grade = requestBody.get("grade") != null ? requestBody.get("grade").get(0) : null;

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

        // 저장 메서드 호출
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("전공 과목 정보가 성공적으로 저장되었습니다.");
    }
    //이수 교양과목 내역 관리 수정
    @PostMapping("{id}/completedCultureCourseSave")
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
    @PostMapping("{id}/completedMajorSave")
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

    //추천 과목
    @PostMapping("{id}/recommendedMajorSubjects")
    public ResponseEntity<List<String>> recommendedMajorSubjects(@PathVariable String id,@RequestParam String grade, @RequestParam String semester) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> enrolledMajorElectives = member.getMajors();
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();
        Map<String, List<String>> coursePrerequisites = new HashMap<>() {{
            put("자료구조", Collections.singletonList("C언어프로그래밍"));
            put("웹 프로그래밍", Collections.singletonList("자료구조"));
            put("팀프로젝트", Collections.singletonList("공학입문설계"));
            put("컴퓨터하드웨어", Collections.singletonList("공학입문설계"));
            put("알고리즘", Collections.singletonList("자료구조")); // 3학년에서만 추천
            // 추가 과목과 선이수 과목 설정
        }};

        // 추천 과목 로직
        for (Map.Entry<String, List<String>> entry : coursePrerequisites.entrySet()) {
            String subject = entry.getKey();
            List<String> prerequisites = entry.getValue();

            // 선이수를 수강하지 않았다면 추천
            if (!enrolledMajorElectives.containsAll(prerequisites)) {
                Subject subjectEntity = subjectRepository.findByname(subject);
                if (subjectEntity != null) {
                    recommendedSubjectsMap.computeIfAbsent("추천 과목", k -> new ArrayList<>()).add(subjectEntity);
                }
            }

            // 3학년에서만 알고리즘 추천
            if (subject.equals("알고리즘") && grade.equals("3학년") && enrolledMajorElectives.contains("공학입문설계")) {
                Subject subjectEntity = subjectRepository.findByname(subject);
                if (subjectEntity != null) {
                    recommendedSubjectsMap.computeIfAbsent("추천 과목", k -> new ArrayList<>()).add(subjectEntity);
                }
            }
        }

        // 추천 과목이 없으면 NO_CONTENT 상태로 응답
        if (recommendedSubjectsMap.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // 추천 과목 반환
        return ResponseEntity.ok((List<String>) recommendedSubjectsMap);
    }
    @PostMapping("{id}/recommendedCoreSubjects")
    public ResponseEntity<Map<String, List<Subject>>> recommendedCoreSubjects(@PathVariable String id) {
        // 회원 조회
        Member member = memberRepository.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> enrolledCoreElectives = member.getCoreElectives();

        // 추천 과목 목록
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("철학과 인간", "한국근현대사의 이해", "역사와 문명", "4차산업혁명을위한비판적사고", "디지털 콘텐츠로 만나는 한국의 문화유산"),
                List.of("세계화와 사회변화", "민주주의와 현대사회", "창업입문", "여성·소수자·공동체", "현대사회와 심리학", "직무수행과 전략적 의사소통"),
                List.of("고전으로읽는 인문학", "예술과창조성", "4차산업혁명시대의예술", "문화리터러시와창의적스토리텔링", "디지털문화의 이해"),
                List.of("환경과 인간", "우주,생명,마음", "SW프로그래밍입문", "인공지능의 세계", "4차산업혁명의 이해", "파이썬을 활용한 데이터 분석과 인공지능")
        );

        // 추천 과목 맵 초기화
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();

        // 추천 과목 리스트 초기화
        for (List<String> subjectList : allRecommendedSubjects) {
            // 해당 그룹에 수강 중인 과목이 포함되어 있으면 전체 그룹 제외
            if (enrolledCoreElectives.stream().anyMatch(subjectList::contains)) {
                continue; // 이 그룹의 과목은 추천하지 않음
            }

            // 해당 그룹의 추천 과목을 찾기
            List<Subject> subjects = new ArrayList<>();
            for (String subjectName : subjectList) {
                Subject subject = subjectRepository.findByname(subjectName);
                if (subject != null) {
                    subjects.add(subject);
                }
            }

            // 추천 과목이 있는 경우 추가
            if (!subjects.isEmpty()) {
                recommendedSubjectsMap.put(subjectList.toString(), subjects);
            }
        }

        // 추천 과목이 없으면 NO_CONTENT 상태 반환
        if (recommendedSubjectsMap.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // 추천 과목 반환
        return ResponseEntity.ok(recommendedSubjectsMap);
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
                    List.of("영어1", "영어2","영어3", "영어4"),
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
            }// 영어 과목 추천
        boolean hasEnglish1 = enrolledCommonElectives.contains("영어1");
        boolean hasEnglish2 = enrolledCommonElectives.contains("영어2");
        boolean hasEnglish3 = enrolledCommonElectives.contains("영어3");
        boolean hasEnglish4 = enrolledCommonElectives.contains("영어4");

        if (!hasEnglish1 && !hasEnglish2 && !hasEnglish3 && !hasEnglish4) {
            recommendedSubjects.add("영어1"); // 영어 과목을 듣지 않았다면 "영어1" 추천
            recommendedSubjects.add("영어3"); // "영어3"도 추천
        } else if (hasEnglish1) {
            recommendedSubjects.add("영어2"); // "영어1"을 들었으면 "영어2" 추천
        } else if (hasEnglish3) {
            recommendedSubjects.add("영어4"); // "영어3"을 들었으면 "영어4" 추천
        }

// 영어 회화 과목 추천
        boolean hasEnglishTalk1 = enrolledCommonElectives.contains("영어회화1");
        boolean hasEnglishTalk2 = enrolledCommonElectives.contains("영어회화2");
        boolean hasEnglishTalk3 = enrolledCommonElectives.contains("영어회화3");
        boolean hasEnglishTalk4 = enrolledCommonElectives.contains("영어회화4");

        if (!hasEnglishTalk1 && !hasEnglishTalk2 && !hasEnglishTalk3 && !hasEnglishTalk4) {
            recommendedSubjects.add("영어회화1"); // 영어 회화 과목을 듣지 않았다면 "영어회화1" 추천
            recommendedSubjects.add("영어회화3"); // "영어회화3"도 추천
        } else if (hasEnglishTalk1) {
            recommendedSubjects.add("영어회화2"); // "영어회화1"을 들었으면 "영어회화2" 추천
        } else if (hasEnglishTalk3) {
            recommendedSubjects.add("영어회화4"); // "영어회화3"을 들었으면 "영어회화4" 추천
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



    }





//    @PostMapping("{id}/recommendedMajorSubjects")
//    public ResponseEntity<List<String>> recommendedMajorSubjects(@PathVariable String id) {
//        Member member = memberRepository.findById(id);
//
//        if (member == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        List<String> enrolledMajorElectives = member.getMajors();
//
//        // 추천 과목군들
//        List<List<String>> allRecommendedSubjects = List.of("C언어프로그래밍","객체지향프로그래밍","자료구조","공학입문설계","팀프로젝트","객체지향프로그래밍1","객체지향프로그래밍1","객체지향프로그래밍1","객체지향프로그래밍1","객체지향프로그래밍1","객체지향프로그래밍1","소프트웨어공학","운영체제","데이터베이스","컴퓨터네트워크","공개SW실무","웹프로그래밍","컴퓨터하드웨어","객체지향프로그래밍2");
//
//        List<String> recommendedSubjects = new ArrayList<>();
//
//        // 1. "성서와인간이해", "현대사회와 기독교 윤리", "종교와과학", "기독교와문화" 리스트 처리
//        List<String> subjectGroup1 = allRecommendedSubjects.get(0);
//        long subjectGroup1Count = enrolledCommonElectives.stream()
//                .filter(subjectGroup1::contains)
//                .count();
//
//        if (subjectGroup1Count == 0) {
//            recommendedSubjects.addAll(subjectGroup1); // 아무것도 듣지 않았다면 모두 추천
//        } else if (subjectGroup1Count == 1) {
//            // 1개만 들었으면 그 과목 제외하고 나머지 과목들 추천
//            recommendedSubjects.addAll(subjectGroup1.stream()
//                    .filter(subject -> !enrolledCommonElectives.contains(subject))
//                    .collect(Collectors.toList()));
//        }
//
//        // 2. "글쓰기", "발표와토의" 리스트 처리
//        List<String> subjectGroup2 = allRecommendedSubjects.get(1);
//        if (enrolledCommonElectives.stream().anyMatch(subjectGroup2::contains)) {
//            // 하나라도 들었다면 이 리스트는 추천하지 않음
//        } else {
//            // 둘 다 안 들었으면 추천
//            recommendedSubjects.addAll(subjectGroup2);
//        }
//
//
//        boolean hasBasicEnglish = enrolledCommonElectives.contains("기초영어");
//        boolean hasEnglish1 = enrolledCommonElectives.contains("영어1");
//        boolean hasEnglish2 = enrolledCommonElectives.contains("영어2");
//        boolean hasEnglish3 = enrolledCommonElectives.contains("영어3");
//        boolean hasEnglish4 = enrolledCommonElectives.contains("영어4");
//
//        if (hasBasicEnglish) {
//            recommendedSubjects.add("영어1"); // "기초영어"를 들었으면 "영어1" 추천
//        } else if (hasEnglish1) {
//            recommendedSubjects.add("영어2"); // "영어1"을 들었으면 "영어2" 추천
//        } else if (hasEnglish2) {
//            // "영어2"를 수강한 경우 아무것도 추천하지 않음
//        } else if (hasEnglish3) {
//            recommendedSubjects.add("영어4"); // "영어3"을 들었으면 "영어4" 추천
//        } else if (hasEnglish4) {
//            // "영어4"를 수강한 경우 아무것도 추천하지 않음
//        }
//
//
//
//        boolean hasEnglishTalk1 = enrolledCommonElectives.contains("영어회화1");
//        boolean hasEnglishTalk2 = enrolledCommonElectives.contains("영어회화2");
//        boolean hasEnglishTalk3 = enrolledCommonElectives.contains("영어회화3");
//        boolean hasEnglishTalk4 = enrolledCommonElectives.contains("영어회화4");
//
//        if (hasEnglishTalk1) {
//            recommendedSubjects.add("영어회화2"); // "기초영어"를 들었으면 "영어1" 추천
//        } else if (hasEnglishTalk2) {
//
//        }
//        else if (hasEnglishTalk3) {
//            recommendedSubjects.add("영어회화4"); // "영어1"을 들었으면 "영어2" 추천
//        } else if (hasEnglishTalk4) {
//
//        }
//        // 7. "4차산업혁명과 미래사회 진로선택", "디지털리터리시의 이해" 리스트 처리
//        List<String> subjectGroup7 = allRecommendedSubjects.get(4);
//        if (enrolledCommonElectives.stream().anyMatch(subjectGroup7::contains)) {
//            // 하나라도 들었으면 추천하지 않음
//        } else {
//            recommendedSubjects.addAll(subjectGroup7);
//        }
//
//        // 추천 과목이 없으면 NO_CONTENT 상태로 응답
//        if (recommendedSubjects.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//        }
//
//        // 추천 과목 반환
//        return ResponseEntity.ok(recommendedSubjects);
//    }








