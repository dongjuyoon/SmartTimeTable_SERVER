package SERVER.SmartTimeTable.Domain;

import java.util.List;

public class Member {

    private String id; // 사용자 ID
    private String name; // 사용자 이름
    private String password; // 비밀번호
    private List<String> majors; // 전공 리스트
    private List<String> coreElectives; // 핵심 교양 과목 리스트
    private List<String> commonElectives; // 공통 교양 과목 리스트
    private int studentId; // 학번
    private String email; // 이메일
    private boolean emailVerified; // 이메일 인증 여부

    // Getter 및 Setter 메서드

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public List<String> getCoreElectives() {
        return coreElectives;
    }

    public void setCoreElectives(List<String> coreElectives) {
        this.coreElectives = coreElectives;
    }

    public List<String> getCommonElectives() {
        return commonElectives;
    }

    public void setCommonElectives(List<String> commonElectives) {
        this.commonElectives = commonElectives;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
