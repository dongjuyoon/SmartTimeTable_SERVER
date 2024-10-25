package SERVER.SmartTimeTable.Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

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
