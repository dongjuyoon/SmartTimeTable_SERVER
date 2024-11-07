package SERVER.SmartTimeTable.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Subject {
    private String classTime; // 요일 및 강의 시간
    private String name; //강의명
    private String professor; //교수명
    private String lectureNumber; //강좌번호
    private List<String> majors; // 모든전공 과목 리스트
    private List<String> coreElectives; // 모든 핵심 교양 과목 리스트
    private List<String> commonElectives;//모든 필수 교양 과목 리스트


}

