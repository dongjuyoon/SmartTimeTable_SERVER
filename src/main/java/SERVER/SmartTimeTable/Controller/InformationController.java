package SERVER.SmartTimeTable.Controller;


import SERVER.SmartTimeTable.Repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {
    private final InformationRepository informationRepository;

    public InformationController(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    @GetMapping("/information")
    public List<String> getInformationInformation() {
        return informationRepository.getDataImages();
    }

}
