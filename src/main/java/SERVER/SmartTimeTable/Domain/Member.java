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
    private List<String> major;//전공
    private List<String> majors; // 전공 과목 리스트
    private List<String> coreElectives; // 핵심 교양 과목 리스트
    private List<String> commonElectives; // 공통 교양 과목 리스트
    private int studentId; // 학번
    private String email; // 이메일
    private boolean emailVerified; // 이메일 인증 여부
    private List<Subject> currentSubject; // 현재 이수중인 과목 리스트

    public Member() {
        this.majors = new ArrayList<>();
        this.coreElectives = new ArrayList<>();
        this.commonElectives = new ArrayList<>();
        this.currentSubject = new ArrayList<>();
    }

    public void addCurrentSubject(Subject subject) {
        this.currentSubject.add(subject);
    }



    public void removeCurrentSubject(String subjectName) {
        this.currentSubject.removeIf(subject -> subject.getName().equals(subjectName));
    }



}