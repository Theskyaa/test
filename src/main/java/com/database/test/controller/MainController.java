package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main1(){
        return "login";
    }


    @RequestMapping(value = "/successful",method = RequestMethod.POST)
    public String successful(@RequestParam("email")String email,
                             @RequestParam("password")String password,
                             Model model){
        model.addAttribute("email",email);
        /*List<Book> booklist=bookRepository.listAll();
        model.addAttribute("list1",booklist);*/
        return "mainMenu";
    }

}
