package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final MemorySubjectRepository subjectRepository = new MemorySubjectRepository();
    private final Member member;

    public SubjectController(Member member) {
        this.member = member;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @PostMapping
    public void addSubject(@RequestBody Subject subject) {
        subjectRepository.addSubject(subject.getName(), subject.getClassTime(), subject.getProfessor(), subject.getLectureNumber(), subject.getDayWeek());
        member.addCurrentSubject(subject);
    }

    @PutMapping("/{name}")
    public void updateSubject(@PathVariable String name, @RequestBody Subject updatedSubject) {
        subjectRepository.deleteSubject(name);
        subjectRepository.addSubject(updatedSubject.getName(), updatedSubject.getClassTime(), updatedSubject.getProfessor(), updatedSubject.getLectureNumber(), updatedSubject.getDayWeek());
        subjectRepository.updateSubject(name, updatedSubject);
        member.removeCurrentSubject(name); // 기존 과목 삭제
        member.addCurrentSubject(updatedSubject); // 수정된 과목 추가
    }

    @DeleteMapping("/{name}")
    public void deleteSubject(@PathVariable String name) {
        subjectRepository.deleteSubject(name);
        member.removeCurrentSubject(name); // 멤버의 과목에서도 삭제
    }




    @GetMapping("/subjects")
    public List<SubList> getSubjects() {
        List<SubList> subList = new ArrayList<>();
        String url = "https://lms.mju.ac.kr/ilos/st/main/course_ing_list_form.acl";

        try {
            // URL에서 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 필요한 데이터 추출 (예: 테이블에서 과목 정보)
            Elements subjectRows = document.select("table tbody tr"); // 테이블의 행 선택

            for (Element row : subjectRows) {
                // 각 행에서 필요한 데이터 추출 (예: 과목명, 교수명, 시간 등)
                String subjectName = row.select("td.subject-name").text(); // 과목명
                String professorName = row.select("td.professor-name").text(); // 교수명
                String time = row.select("td.time").text(); // 시간 (예시로 td.time을 사용)

                // SubList 객체 생성 및 추가
                subList.add(new SubList(subjectName, time, professorName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 오류 발생 시 null 반환
        }

        return subList;
    }

    // SubList 클래스 정의
    public static class SubList {
        private String subjectName;
        private String time;
        private String professorName;

        public SubList(String subjectName, String time, String professorName) {
            this.subjectName = subjectName;
            this.time = time;
            this.professorName = professorName;
        }

        // Getter 메서드
        public String getSubjectName() {
            return subjectName;
        }

        public String getTime() {
            return time;
        }

        public String getProfessorName() {
            return professorName;
        }
    }
}


