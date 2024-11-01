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
import java.util.Optional;

@RestController
@RequestMapping("/api/information")
public class InformationController {
    private final InformationRepository informationRepository;

    public InformationController(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    /**
     * 공모전 정보를 외부 사이트에서 가져오는 API
     * @return 가져온 공모전 정보 리스트
     */
    @GetMapping("/information")
    public List<Information> getInformationInformation() {
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

                // Information 객체 생성 및 리스트에 추가
                InformationList.add(new Information(informationName, homepageLink, applicationPeriod, photoLink));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 발생 시 HTTP 500 상태 코드와 메시지 반환
            return (List<Information>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터를 가져오는 중 오류가 발생했습니다.");
        }

        return InformationList; // 가져온 정보 리스트 반환
    }

    /**
     * 새로운 공모전 정보를 저장하는 API
     * @param information 저장할 공모전 정보
     * @return 저장 성공 메시지
     */
    @PostMapping
    public ResponseEntity<String> createInformation(@RequestBody Information information) {
        informationRepository.save(information); // 공모전 정보 저장
        return ResponseEntity.status(HttpStatus.CREATED).body("공모전 정보가 저장되었습니다.");
    }

    /**
     * 특정 ID에 해당하는 공모전 정보를 조회하는 API
     * @param id 조회할 공모전의 ID
     * @return 공모전 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<Information> getInformation(@PathVariable String id) {
        Optional<Information> information = informationRepository.findByName(id); // ID로 공모전 정보 검색
        if (information.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 정보가 없으면 404 반환
        }
        return (ResponseEntity<Information>) ResponseEntity.ok(); // 조회된 정보 반환
    }

    /**
     * 모든 공모전 정보를 조회하는 API
     * @return 공모전 정보 리스트
     */
    @GetMapping
    public ResponseEntity<List<Information>> getAllInformation() {
        List<Information> informationList = informationRepository.findAll(); // 모든 공모전 정보 검색
        return ResponseEntity.ok(informationList); // 정보 리스트 반환
    }

    /**
     * 특정 ID에 해당하는 공모전 정보를 업데이트하는 API
     * @param id 수정할 공모전의 ID
     * @param updatedInformation 수정된 공모전 정보
     * @return 수정 성공 메시지
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateInformation(@PathVariable String id, @RequestBody Information updatedInformation) {
        informationRepository.update(id, updatedInformation); // 공모전 정보 업데이트
        return ResponseEntity.ok("공모전 정보가 수정되었습니다."); // 수정 성공 메시지 반환
    }

    /**
     * 특정 ID에 해당하는 공모전 정보를 삭제하는 API
     * @param id 삭제할 공모전의 ID
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInformation(@PathVariable String id) {
        informationRepository.delete(id); // 공모전 정보 삭제
        return ResponseEntity.ok("공모전 정보가 삭제되었습니다."); // 삭제 성공 메시지 반환
    }
}
