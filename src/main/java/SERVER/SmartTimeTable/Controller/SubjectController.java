package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Member;
import SERVER.SmartTimeTable.Domain.Subject;
import SERVER.SmartTimeTable.Repository.MemoryMemberRepository;
import SERVER.SmartTimeTable.Repository.MemorySubjectRepository;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
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

    // 강의 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addSubject(@RequestBody List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subjectRepository.findByname(subject.getName()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 강의입니다.");
            }
            subjectRepository.save(subject);
        }
        return ResponseEntity.ok("강의가 추가되었습니다.");
    }

    // 특정 강의를 멤버의 시간표에 추가하는 API
    @PostMapping("/addToMember")
    public ResponseEntity<String> addSubjectToMember(@RequestBody AddSubjectRequest request) {
        if (request.getMember() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 정보가 제공되지 않았습니다.");
        }

        Subject subject = subjectRepository.findByname(request.getSubjectName());
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의를 찾을 수 없습니다.");
        }

        // 요청에서 멤버 ID를 가져와 해당 멤버를 메모리에서 조회
        Member member = memberRepository.findById(request.getMember().getId());
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원이 존재하지 않습니다.");
        }

        member.addCurrentSubject(subject); // 강의를 멤버의 시간표에 추가
        memberRepository.save(member); // 업데이트된 멤버 객체 저장

        return ResponseEntity.ok("강의가 시간표에 추가되었습니다.");
    }

    @Getter
    @Setter
    public static class AddSubjectRequest {
        private String subjectName; // 추가할 강의명
        private Member member; // 선택한 멤버 정보
    }
}
