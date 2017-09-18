package com.daniel.test.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.daniel.test.springboot.entity.Member;
import com.daniel.test.springboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/18 17:24
 */
@RestController
public class TestController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get")
    public String get(@RequestParam Long memberId){

        Member member = memberService.getMember(memberId);
        System.out.println(JSONObject.toJSONString(member));

        return "success";
    }
}
