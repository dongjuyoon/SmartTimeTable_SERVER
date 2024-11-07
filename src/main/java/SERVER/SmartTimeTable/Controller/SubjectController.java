package SERVER.SmartTimeTable.Controller; // 패키지 이름 수정


import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
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
    private final MemoryMemberRepository memberRepository; // 인스턴스 변수 추가

    public SubjectController(MemorySubjectRepository subjectRepository, MemoryMemberRepository memberRepository) {
        this.subjectRepository = subjectRepository;
        this.memberRepository = memberRepository; // 초기화
    }

    // 모든 강의 목록을 반환하는 API
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // 강의 추가 API
//    @PostMapping("/add")
//    public ResponseEntity<String> addSubject(@RequestBody List<Subject> subjects) {
//        for (Subject subject : subjects) {
//            if (subjectRepository.findByname(subject.getName()) != null) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 강의입니다.");
//            }
//            subjectRepository.save(subject);
//        }
//        return ResponseEntity.ok("강의가 추가되었습니다.");
//    }


    //전체과목 돌려줌
    @GetMapping("/allReturnSubjects")
    public ResponseEntity<List<Subject>> allReturnSubjects(@PathVariable String id) {
        Member member = memberRepository.findById(id);


        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(subjectRepository.findAll());
    }

    //모든 전공 과목 돌려줌
    @GetMapping("/majors")
    public ResponseEntity<List<Subject>> majors(@PathVariable String id) {
        Member member = memberRepository.findById(id);


        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(subjectRepository.getMajors());
    }
    //모든 공통교양 과목 돌려줌
    @GetMapping("/commonElectives")
    public ResponseEntity<List<Subject>> commonElectives(@PathVariable String id) {
        Member member = memberRepository.findById(id);


        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(subjectRepository.getCommonElectives());
    }
    //모든 핵심 과목 돌려줌
    @GetMapping("/majors")
    public ResponseEntity<List<Subject>> coreElectives(@PathVariable String id) {
        Member member = memberRepository.findById(id);


        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(subjectRepository.getCoreElectives());
    }




}
