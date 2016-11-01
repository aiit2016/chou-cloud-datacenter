package chou.cloud.datacenter.chou.cloud.datacenter;

/**
 * Created by Koala Cheung on 2016/10/31.
 */

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
