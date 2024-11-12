package SERVER.SmartTimeTable.Controller; // 패키지 이름 수정


import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
import SERVER.SmartTimeTable.Repository.SubjectRepository;
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

    public SubjectController(MemorySubjectRepository subjectRepository, MemoryMemberRepository memberRepository) {
        this.subjectRepository = subjectRepository;
    }

    // 모든 강의 목록을 반환하는 API
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }



    //전체과목 돌려줌
    @GetMapping("/allReturnSubjects")
    public ResponseEntity<List<Subject>> allReturnSubjects() {
        return ResponseEntity.ok(subjectRepository.findAll());
    }

    //모든 전공 과목 돌려줌
    @GetMapping("/majors")
    public ResponseEntity<List<Subject>> majors() {
        return ResponseEntity.ok(subjectRepository.getMajors());
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
