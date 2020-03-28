package com.aluji.controller;

import com.aluji.entities.User;
import com.aluji.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/user/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("confirmpassword") String confirmpassword,
                           @RequestParam("phonenumber") String telnum,
                           Map<String,Object> map) throws IOException {
        if(!password.equals(confirmpassword)){
            map.put("registermsg","两次输入密码不一致！");
            return "register";
        }
        else if(registerService.alreadyhasUser(username)){
            map.put("registermsg","用户名已存在，请换一个！");
            return "register";
        }
        else{
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(password);
            user.setUserTel(telnum);
            registerService.addUser(user);
            return "login";
        }

    }

}
