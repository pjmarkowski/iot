package pl.lodz.p.edu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

import java.util.Map;

@Controller
public class EntryPointController {

    @RequestMapping("/")
    public String index() {
        return "welcome";
    }
    @RequestMapping("/statistics")
    public String statistics() {
        return "statistics";
    }
}
