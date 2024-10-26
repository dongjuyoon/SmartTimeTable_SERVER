package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Subject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemorySubjectRepository implements SubjectRepository {
    private final Map<String, Subject> subjectMap = new HashMap<>();

    @Override
    public Subject findByname(String name) {
        return subjectMap.get(name);
    }

    @Override
    public Subject findBylectureNumber(String lectureNumber) {
        return subjectMap.get(lectureNumber);
    }

    @Override
    public Subject findByprofessor(String professor) {
        return subjectMap.get(professor);
    }

    @Override
    public void save(Subject subject) {
        subjectMap.put(subject.getName(), subject);
    }


//    public void addSubject(Subject subject) {
//        subjectMap.put(subject.getName(), subject);
//    }

    @Override
    public void updateSubject(String name, Subject updatedSubject) {
        subjectMap.put(name, updatedSubject);
    }

    @Override
    public void deleteSubject(String name) {
        subjectMap.remove(name);
    }

    @Override
    public List<Subject> findAll() {
        return new ArrayList<>(subjectMap.values());
    }

    @Override
    public void addSubject(String name, String classTime, String professor, String lectureNumber, String dayWeek) {

    }
}