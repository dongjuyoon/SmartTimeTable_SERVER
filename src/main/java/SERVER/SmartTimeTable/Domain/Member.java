package SERVER.SmartTimeTable.Domain;

public class Member {

    private String id; // ID를 String으로 변경
    private String name;
    private String password; // 비밀번호를 String으로 수정
    private String major;
    private int studentId; // 대문자 변경
    private String email; // e_mail을 String으로 수정

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSTUDENT_ID() {
        return studentId;
    }

    public void setSTUDENT_ID(int studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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
