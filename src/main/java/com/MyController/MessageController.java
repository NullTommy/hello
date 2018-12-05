package com.MyController;

import com.MyUtils.MessageSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by panghaichen on 2018-06-27 14:56
 **/

@Controller
@RequestMapping("/message")
public class MessageController {

    @RequestMapping(value = "/message.do",method = RequestMethod.GET)
    public void index() {
        //http://localhost/message/message.do
        System.out.println(MessageSourceUtils.getMessage("welcome"));
    }

}