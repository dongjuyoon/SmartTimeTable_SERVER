package SERVER.SmartTimeTable.Domain;

import java.util.List;

public class Member {

    private String id; // ID를 String으로 변경
    private String name;
    private String password; // 비밀번호를 String으로 수정
    private String major; // 전공
    private List<String> majors; // 전공 리스트
    private List<String> electiveCourses; // 교양 과목 리스트
    private int studentId; // 대문자 변경
    private String email; // e_mail을 String으로 수정

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudentId() { // 메서드 이름 수정
        return studentId;
    }

    public void setStudentId(int studentId) { // 메서드 이름 수정
        this.studentId = studentId;
    }

    public List<String> getMajors() { // 전공 리스트 반환
        return majors;
    }

    public void setMajors(List<String> majors) { // 전공 리스트 설정
        this.majors = majors;
    }

    public String getMajor() { // 전공 리스트 반환
        return major;
    }

    public void setMajor(String major) { // 전공 리스트 설정
        this.major = major;
    }

    public List<String> getElectiveCourses() { // 교양 과목 리스트 반환
        return electiveCourses;
    }

    public void setElectiveCourses(List<String> electiveCourses) { // 교양 과목 리스트 설정
        this.electiveCourses = electiveCourses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() { // ID를 String으로 반환
        return id;
    }

    public void setId(String id) { // ID를 String으로 설정
        this.id = id;
    }
}
