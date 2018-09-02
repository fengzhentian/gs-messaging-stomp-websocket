package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/greeting")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @SubscribeMapping("/macro")
    public Greeting handleSubscription() {
        Greeting greeting = new Greeting("i am a msg from SubscribeMapping('/macro').");
        return greeting;
    }

    @RequestMapping(path = "/greetings")
    @ResponseBody
    public Greeting greet() {
        Greeting greeting = new Greeting("[" + new Date() + "]");
        this.template.convertAndSend("/topic/feed.10.2", greeting);
        return greeting;
    }

}
