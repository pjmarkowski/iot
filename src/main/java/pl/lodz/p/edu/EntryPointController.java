package pl.lodz.p.edu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

import java.util.Map;

@Controller
public class EntryPointController {
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("message", this.message);
        return "welcome";
    }
    @RequestMapping("/statistics")
    public String statistics(Map<String, Object> model) {
        model.put("message", this.message);
        return "statistics";
    }
}
