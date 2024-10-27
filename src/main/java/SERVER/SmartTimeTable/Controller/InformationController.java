package SERVER.SmartTimeTable.Controller;

import SERVER.SmartTimeTable.Domain.Information;
import SERVER.SmartTimeTable.Repository.InformationRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/information")
public class InformationController {
    private final InformationRepository informationRepository;

    public InformationController(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    @GetMapping("/information")
    public List<Information> getSubjects() {
        List<Information> InformationList = new ArrayList<>();
        String url = "https://www.thinkcontest.com/thinkgood/user/contest/index.do#PxyyoRLHIcgvNg6HiHNz_mp_cuclMrohRDGEjn6hvDsggetrTQxNWBBQR1mPnaRxjI93xVlR_kFjCl9g5hFBO1N6UGMkDhLA2ecFAf6UhFU";

        try {
            // URL에서 HTML 문서 가져오기
            Document document = Jsoup.connect(url).get();

            // 필요한 데이터 추출 (예: 테이블에서 과목 정보)
            Elements informationRows = document.select("table tbody tr");

            for (Element row : informationRows) {
                String informationName = row.select("td.information-name").text();
                String applicationPeriod = row.select("td.applicationPeriod").text();
                String homepageLink = row.select("td.homepageLink").text();
                String photoLink = row.select("td.photoLink").text();

                // SubList 객체 생성 및 추가
                InformationList.add(new Information(informationName, homepageLink, applicationPeriod, photoLink));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 오류 발생 시 null 반환
        }

        return InformationList;
    }

    @PostMapping
    public ResponseEntity<String> createInformation(@RequestBody Information information) {
        informationRepository.save(information);
        return ResponseEntity.status(HttpStatus.CREATED).body("공모전 정보가 저장되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Information> getInformation(@PathVariable String id) {
        Information information = informationRepository.findByName(id);
        if (information == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(information);
    }

    @GetMapping
    public ResponseEntity<List<Information>> getAllInformation() {
        List<Information> informationList = informationRepository.findAll();
        return ResponseEntity.ok(informationList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInformation(@PathVariable String id, @RequestBody Information updatedInformation) {
        informationRepository.update(id, updatedInformation);
        return ResponseEntity.ok("공모전 정보가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInformation(@PathVariable String id) {
        informationRepository.delete(id);
        return ResponseEntity.ok("공모전 정보가 삭제되었습니다.");
    }
}
