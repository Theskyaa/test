package com.database.test.controller;


import com.database.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddBookController {


    /*@Autowired
    BookRepository bookRepository;

    @ResponseBody
    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    public boolean addBook(@RequestParam("bookName")String bookName,
                           @RequestParam("bookAuthor")String bookAuthor,
                           @RequestParam("bookOwner")String bookOwner,
                           @RequestParam("bookReading")String bookReading,
                           @RequestParam("bookIntroduction")String bookIntroduction,
                           @RequestParam("bookScore")Integer bookScore,
                           @RequestParam("houseId")Integer houseId){

        if (bookReading.length()==0){
            bookReading="null";
        }

        int num=bookRepository.findAll().size();
        int result=bookRepository.insertBook(num+1,bookName,bookAuthor,bookOwner,bookReading,bookIntroduction,bookScore,houseId);

        if (result==1){
            return true;
        }
        return false;
    }*/

}
