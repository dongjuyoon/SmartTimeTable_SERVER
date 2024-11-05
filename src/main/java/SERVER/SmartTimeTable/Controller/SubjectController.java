package SERVER.SmartTimeTable.Controller; // 패키지 이름 수정


import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final MemorySubjectRepository subjectRepository;
    @Autowired
    public SubjectController(MemorySubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // 모든 강의 목록을 반환하는 API
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // 강의 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addSubject(@RequestBody Subject subject) {
        subjectRepository.save(subject);
        System.out.println("Added subject: " + subject.getName()); // 로그 추가
        return ResponseEntity.ok("강의가 추가되었습니다.");
    }

    // 특정 강의를 멤버의 시간표에 추가하는 API
    @PostMapping("/addToMember")
    public ResponseEntity<String> addSubjectToMember(@RequestBody AddSubjectRequest request) {
        Subject subject = subjectRepository.findByname(request.getSubjectName());
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의를 찾을 수 없습니다.");
        }

        Member member = request.getMember(); // 요청에서 멤버 정보 가져오기
        member.addCurrentSubject(subject); // 강의를 멤버의 시간표에 추가

        return ResponseEntity.ok("강의가 시간표에 추가되었습니다.");
    }



    @Getter
    @Setter
    public static class AddSubjectRequest {
        private String subjectName; // 추가할 강의명
        private Member member; // 선택한 멤버 정보
    }
}


//    @GetMapping("/subjects")
//    public Subject findSubjectByName(@RequestParam String name) {
//        return subjectRepository.findByname(name);
//    }
//
//    @GetMapping("/subjects")
//    public Subject findByProfessor(@RequestParam String professor) {
//        return subjectRepository.findByname(professor);
//    }
//
//    @GetMapping("/subjects")
//    public Subject findByLectureNumber(@RequestParam String lectureNumber) {
//        return subjectRepository.findByname(lectureNumber);
//    }


