package com.aluji.controller;

import com.aluji.entities.*;
import com.aluji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    StreetAdminService streetAdminService;

    @Autowired
    StoreAdminService storeAdminService;

    @Autowired
    StreetService streetService;

    @Autowired
    StoreService storeService;

    @Autowired
    ItemService itemService;

    //@RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session) throws IOException {

      System.out.println(username);
      int logininfo = loginService.checklogininfo(username,password);
        System.out.println("login info: " + logininfo);
      if(logininfo == 1){
          //普通用户登陆 进商业街界面

            session.setAttribute("loginUser",username);
          return "redirect:/main.html";
      }
      else if(logininfo == 2){
          //游客登录
          session.setAttribute("loginUser","游客用户");
          return "redirect:/main.html";
      }
      else{
          map.put("loginmsg","用户名密码错误，请重新登陆");
          return "login";
        }
    }

    @PostMapping(value = "/visitor/login")
    public String visitorlogin(
                        Map<String,Object> map, HttpSession session) throws IOException {


        session.setAttribute("loginUser","游客用户");
        return "redirect:/main.html";
    }

    @PostMapping(value = "/admin/login")
    public String adminlogin(@RequestParam("username") Integer adminid,
                             @RequestParam("password") String password,
                             @RequestParam("admintype") String admintype,
                             Map<String,Object> map,HttpSession session) throws IOException {

        //商业街管理员
         if(admintype.equals("streetadmin") && loginService.checkStreetAdminLoginInfo(adminid,password) == 1) {
            StreetAdmin currentstreetAdmin = streetAdminService.getStreetAdminById(adminid);
            //加入当前商业街信息
            //map.put("currentstreetadmin",currentstreetAdmin);
            session.setAttribute("currentstreetadmin",currentstreetAdmin);
            List<Store> list = storeService.getAllStoreInStreet(currentstreetAdmin.getStreetId());
            //map.put("currentstores",list);
            for (Store s:list
                 ) {
                System.out.println(s);
            }
            session.setAttribute("currentstores",list);

            return "streetadmin";
        }
        //商铺管理员
        else if(admintype.equals("storeadmin") && loginService.checkStoreAdminLoginInfo(adminid,password) == 1){
            StoreAdmin currentstoreAdmin = storeAdminService.getStoreAdminById(adminid);
            //加入当前商铺信息
            map.put("currentstoreadmin",currentstoreAdmin);

            List<Item> list = itemService.getAllItemInStore(currentstoreAdmin.getStoreId());
            map.put("currentitems",list);
            return "storeadmin";
        }
        map.put("adminloginmsg","账号密码错误，请重新登录");
        return "adminlogin";
//        return "/../adminlogin.html";
    }


    //去管理员登录界面
    @RequestMapping(value = "/toadminlogin")
    public String toadminlogin(){
        return "adminlogin.html";
    }

    @RequestMapping(value = "/toregister")
    public String register(Map<String,Object> map, HttpSession session){
        System.out.println("in /toregister controller");
            return "register";
    }
}
