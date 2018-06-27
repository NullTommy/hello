package MyController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by panghaichen on 2018-06-27 14:56
 **/

@Controller
public class MainController {
    @RequestMapping(value = "/test.do",method = RequestMethod.GET)
    public String index() {
        //http://localhost:8080/test.do
        return "index";
    }

    @RequestMapping(value = "/test2.do",method = RequestMethod.GET)
    public String index2() {
        //http://localhost:8080/test2.do
        return "jsp/index";
    }
}
