package SERVER.SmartTimeTable.Domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Subject {
    private String classTime; //강의 시간
    private String name; //강의명
    private String professor; //교수명
    private String lectureNumber; //강좌번호
    private boolean recommendation = false; //추천
    private String dayWeek; // 요일


}
