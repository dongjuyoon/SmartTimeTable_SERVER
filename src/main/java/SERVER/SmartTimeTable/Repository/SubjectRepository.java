package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SubjectRepository {
    final Map<String, Subject> memberMap = new HashMap<>();

    Subject findByName(String name);

    Subject findByLectureNumber(String lectureNumber);

    Subject findByProfessor(String professor);

    void save(Subject subject);

/*
    void fetchCourseData();
*/
    /*
        void addSubject(String name, String classTime, String professor, String lectureNumber, String dayWeek);
    */
    void recommendPrerequisites(List<String> enrolledMajorElectives, Map<String, List<Subject>> recommendedSubjectsMap, List<String> prerequisites);
    List<Subject> findAll();

    void addRecommendedSubject(String subject, Map<String, List<Subject>> recommendedSubjectsMap);
    Map<String, List<String>> getCoursePrerequisites();
    void recommendSubjectsByGradeAndSemester(String grade, String semester, String subject, List<String> enrolledMajorElectives, Map<String, List<Subject>> recommendedSubjectsMap);
    void updateSubject(String name, Subject updatedSubject);
    void deleteSubject(String name);
    List<Subject> getCoreElectives();
    List<Subject> getMajors();
    List<Subject> getCommonElectives();
    List<Subject> getAllElectives();
    Map<String, List<Subject>> findRecommendedCoreSubjects(Member member);
    List<String> findRecommendedCommonSubjects(Member member);

    List<String> getAllMajors();
}
