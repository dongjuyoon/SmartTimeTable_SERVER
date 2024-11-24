package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
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

    @Autowired
    public SubjectController(MemorySubjectRepository subjectRepository, MemoryMemberRepository memberRepository) {
        this.subjectRepository = subjectRepository;
        this.memberRepository = memberRepository; // 초기화
    }

    // 모든 강의 목록을 반환하는 API
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    //전체과목 돌려줌 돌려줌 다 수정해야함
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
    public ResponseEntity<List<Subject>> majors() {
        List<Subject> majors = subjectRepository.getMajors();

        if (majors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        }
        return ResponseEntity.ok(majors);
    }
    //모든 공통교양 과목 돌려줌
    @GetMapping("/commonElectives")
    public ResponseEntity<List<Subject>> commonElectives() {

        return ResponseEntity.ok(subjectRepository.getCommonElectives());
    }
    //모든 핵심 과목 돌려줌
    @GetMapping("/coreElectives")
    public ResponseEntity<List<Subject>> coreElectives() {

        return ResponseEntity.ok(subjectRepository.getCoreElectives());
    }
}


