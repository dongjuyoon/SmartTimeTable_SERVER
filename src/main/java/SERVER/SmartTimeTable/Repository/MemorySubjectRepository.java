package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Subject;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemorySubjectRepository implements SubjectRepository {
    private final Map<String, Subject> subjectMap = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate; // RestTemplate 주입

    @Override
    public Subject findByname(String name) {
        return subjectMap.get(name);
    }

    @Override
    public Subject findByLectureNumber(String lectureNumber) {
        return subjectMap.get(lectureNumber);
    }

    @Override
    public Subject findByProfessor(String professor) {
        return subjectMap.get(professor);
    }

    @Override
    public void save(Subject subject) {
        subjectMap.put(subject.getName(), subject);
    }

    @PostConstruct
    public void fetchAndSaveSubjects() {
        try {
            String flaskApiUrl = "http://127.0.0.1:5000/api/course_data"; // Flask API 주소
            List<Subject> subjects = restTemplate.getForObject(flaskApiUrl, List.class);

            if (subjects != null) {
                for (Subject subject : subjects) {
                    save(subject); // Subject 객체 저장
                }
            }
        } catch (Exception e) {
            // 예외 처리 로직
            System.err.println("API 호출 중 오류 발생: " + e.getMessage());
        }
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