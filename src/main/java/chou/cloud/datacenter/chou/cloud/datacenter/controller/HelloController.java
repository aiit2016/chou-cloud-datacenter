package chou.cloud.datacenter.chou.cloud.datacenter.controller;

/**
 * Created by Koala Cheung on 2016/10/31.
 */

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
