package SERVER.SmartTimeTable.Controller;


import SERVER.SmartTimeTable.Repository.InformationRepository;
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
