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
