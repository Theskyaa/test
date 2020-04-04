package com.database.test.controller;

import com.database.test.entity.User;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.BookRepository;
import com.database.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SignAndLoginController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowRecordsRepository borrowingRecordsRepository;

    /*@ResponseBody
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public List<Book> Login2(){
        //System.out.println(libraryItemRepository.listAll());
        List<Book> books =bookRepository.listAll();
        return books;
    }*/

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public boolean signup(@RequestParam("email")String email,
                          @RequestParam("password")String password,
                          @RequestParam("username")String username){
        List<User> list=userRepository.findByEmail(email);
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
    }





   /* @RequestMapping(value = "/",method = RequestMethod.GET)
    public String Login1(Model model){
        List<Book> listAll=bookRepository.listAll();
        model.addAttribute("list1", listAll);
        return "Login.html";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login.html";
    }

    @RequestMapping(value = "/sign",method = RequestMethod.GET)
    public String sign(){
        return "signup.html";
    }


    @RequestMapping(value = "/success",method = RequestMethod.POST)
    public String login_success(@RequestParam("email")String email,
                                @RequestParam("password")String password,
                                Model model){

        List<User> list=userRepository.findByEmail(email);
        List<Book> listAll=bookRepository.listAll();
        model.addAttribute("list1", listAll);

        if (list.isEmpty()){
            System.out.println("null");
            return "login.html";
        }else{
            if (list.get(0).getPassword().equals(password)){

                return "Dashboard.html";
            }else {
                System.out.println("password error");
                return "login.html";
            }
        }

    }*/

}
