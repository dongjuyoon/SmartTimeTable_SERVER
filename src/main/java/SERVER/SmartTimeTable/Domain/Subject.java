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

}

