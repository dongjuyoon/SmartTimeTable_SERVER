package SERVER.SmartTimeTable.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    @GetMapping("/signup")
    public String signup() {
        return "signup"; // signup.html 파일을 반환
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 파일을 반환
    }

    @GetMapping("/findId")
    public String findId() {
        return "findId"; // login.html 파일을 반환
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "findPassword"; // login.html 파일을 반환
    }


    @GetMapping("/add")
    public String addSubject() {
        return "add"; // login.html 파일을 반환
    }

}
