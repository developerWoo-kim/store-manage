package gwkim.storemanage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/view/sign-up")
    public String sign_up() {
        return "auth-signup";
    }
}
