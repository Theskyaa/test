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

    /*@RequestMapping(value = "/bookBorrow",method = RequestMethod.POST)
    public boolean bookBorrow(@RequestParam()){

    }*/

    @RequestMapping(value = "/borrowbook",method = RequestMethod.GET)
    public String borrowbook1(Model model){
        List<Book> booklist=bookRepository.listAll();
        model.addAttribute("list2",booklist);
        return "borrow";
    }

    @RequestMapping(value = "/returnbook1",method = RequestMethod.GET)
    public String returnbook1(Model model){
        List<Book> bookList=bookRepository.listBorrowedBook();
        model.addAttribute("list3",bookList);
        return "return";
    }

}
