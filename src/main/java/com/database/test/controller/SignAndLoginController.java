package com.database.test.controller;

import com.database.test.entity.User;
import com.database.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SignAndLoginController {

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
                //request.getSession().setAttribute("email",email);
                session.setAttribute("currentEmail",email);
                session.setAttribute("currentUsername",users.get(0).getUsername());
                //model.addAttribute("email",email);
                //return "mainMenu";
                System.out.println("success");
                return "redirect:/groupMenu";
            }
        }
        return "redirect:/login";
    }



    @RequestMapping(value = "/registerSuccess",method = RequestMethod.POST)
    public String register(@RequestParam("email")String email,
                           @RequestParam("password")String password,
                           @RequestParam("username")String username){

        List<User> users=userRepository.selectByEmail(email);
        if (users.size()==0){
            System.out.println(email);
            System.out.println(password);
            System.out.println(username);
            userRepository.insertUser(email,username,password);
            return "redirect:/login";
        }else {
            return "user/register.html";
        }
    }





    /*@ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public boolean signup(@RequestParam("email")String email,
                          @RequestParam("password")String password,
                          @RequestParam("username")String username){
        List<User> list=userRepository.selectByEmail(email);
        if (list.isEmpty()){
            User newUser=new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUsername(username);
            userRepository.save(newUser);
            System.out.println(email+" sign up successfully");
            return true;
        }else {
            System.out.println(email+" has already sign up");
            return false;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/userlogin",method = RequestMethod.POST)
    public boolean signin(@RequestParam("email")String email,
                          @RequestParam("password")String password){
        List<User>list=userRepository.findByEmail(email);
        if (list.isEmpty()){
            System.out.println("error");
            return false;
        }else {
            if (list.get(0).getPassword().equals(password)){
                System.out.println(email+" sign in");
                return true;
            }
            return false;
        }
    }*/


}
