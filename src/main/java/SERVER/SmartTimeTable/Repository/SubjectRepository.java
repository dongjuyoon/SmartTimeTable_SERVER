package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SubjectRepository {
    final Map<String, Subject> memberMap = new HashMap<>();

    Subject findByname(String name);

    Subject findBylectureNumber(String lectureNumber);

    Subject findByprofessor(String professor);

    void save(Subject subject);

    List<Subject> findAll();

    //List<Subject> addsubject(String name,String subject,String classTime ,String dayWeek);

    void addSubject(String name, String classTime, String professor, String lectureNumber, String dayWeek);
    void updateSubject(String name, Subject updatedSubject);
    void deleteSubject(String name);
}
