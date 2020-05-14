package com.database.test.controller;

import com.database.test.entity.User;
import com.database.test.repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "user/login.html";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "user/register.html";
    }


    @RequestMapping(value = "/mainMenu",method = RequestMethod.GET)
    public String mainMenu(){
        return "mainMenu";
    }

    @RequestMapping(value = "/groupMenu",method = RequestMethod.GET)
    public String groupMenu(){
        return "groupMenu";
    }

    @RequestMapping(value = "/loginSuccess",method = RequestMethod.POST)
    public String loginSuccess(@RequestParam("email")String email,
                               @RequestParam("password")String password,
                               Model model,
                               HttpSession session
                               /*HttpServletRequest request,
                               HttpServletResponse response*/){
        List<User> users=userRepository.selectByEmail(email);
        if (users.size()==0){
            return "redirect:/login";
        }else {
            if (users.get(0).getPassword().equals(password)){
                session.setAttribute("currentEmail",email);
                session.setAttribute("currentUsername",users.get(0).getUsername());
                System.out.println("success");
                return "redirect:/groupMenu";
            }
        }
        return "redirect:/login";
    }



    @RequestMapping(value = "/registerSuccess",method = RequestMethod.POST)
    public String register(@RequestParam("email")String email,
                           @RequestParam("password")String password,
                           @RequestParam("username")String username,
                           @RequestParam("gender")String gender,
                           @RequestParam("QQ")String QQ,
                           @RequestParam("userTel")String Tel,
                           @RequestParam("introduction")String introduction){

        List<User> users=userRepository.selectByEmail(email);
        if (users.size()==0){
            System.out.println(email);
            System.out.println(password);
            System.out.println(username);
            userRepository.insertUser(email,username,password,gender,QQ,Tel,introduction);
            return "redirect:/login";
        }else {
            return "user/register.html";
        }
    }


    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public String getUserInfo(HttpSession session,
                              Model model){
        String email= (String) session.getAttribute("currentEmail");
        List<User> users=userRepository.selectByEmail(email);
        if (users.size()>0){
            User user=users.get(0);
            model.addAttribute("userEmail",email);
            model.addAttribute("userName",user.getUsername());
            model.addAttribute("userGender",user.getGender());
            model.addAttribute("userTel",user.getTel());
            model.addAttribute("userQQ",user.getQQ());
            model.addAttribute("userIntroduction",user.getIntroduction());
        }
        return "user/userInfoPage.html";
    }

    @ResponseBody
    @RequestMapping(value = "/userInfoChange",method = RequestMethod.POST)
    public boolean userInfoChange(@RequestParam("userEmail")String userEmail,
                                  @RequestParam("userName")String userName,
                                  @RequestParam("userGender")String userGender,
                                  @RequestParam("userQQ")String userQQ,
                                  @RequestParam("userTel")String userTel,
                                  @RequestParam("userIntroduction")String userIntroduction){
        System.out.println(userEmail);
        userRepository.updateUserInfo(userEmail,userName,userGender,userQQ,userTel,userIntroduction);
        return true;
    }

}
