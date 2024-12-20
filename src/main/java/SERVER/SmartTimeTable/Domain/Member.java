package SERVER.SmartTimeTable.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Member {

    private String id; // 사용자 ID
    private String name; // 사용자 이름
    private String password; // 비밀번호
    private String major;//전공
    private List<String> majors; // 전공 과목 리스트
    private List<String> coreElectives; // 핵심 교양 과목 리스트
    private List<String> commonElectives; // 공통 교양 과목 리스트
    private String studentId; // 학번
    private String email; // 이메일
    private List<Subject> currentSubject;// 현재 이수중인 과목 리스트
    private String semester; //현재 학기
    private String grade;// 현재 학년

    public Member() {
        this.majors = new ArrayList<>();
        this.coreElectives = new ArrayList<>();
        this.commonElectives = new ArrayList<>();
        this.currentSubject = new ArrayList<>();
    }








}