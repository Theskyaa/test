package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class BookReviewController {

    /*@Autowired
    BookReviewRepository bookReviewRepository;

    @Autowired
    BookRepository bookRepository;

    @ResponseBody
    @RequestMapping(value = "/makeComments",method = RequestMethod.POST)
    public boolean makeComments(@RequestParam("email")String email,
                                @RequestParam("bookName")String bookName,
                                @RequestParam("reviewText")String reviewText){
        int num=bookReviewRepository.findAll().size();
        Book currentBook=bookRepository.selectByBookName(bookName).get(0);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String time=simpleDateFormat.format(calendar.getTime());

        int result=bookReviewRepository.insertBookReview(num+1,email,currentBook.getBookId(),time,reviewText);

        if (result==1){
            return true;
        }

        return false;
    }*/


    /*@ResponseBody
    @RequestMapping(value = "/listCommentsByBookName",method = RequestMethod.POST)
    public List<Object> listCommentsByBookName(@RequestParam("bookName")String bookName){
        //return bookReviewRepository.selectBookReviewByBookID(bookID);
        return bookReviewRepository.selectBookReviewBuBookName(bookName);
    }

    @ResponseBody
    @RequestMapping(value = "/listCommentsByEmail",method = RequestMethod.POST)
    public List<Object> listCommentsByEmail(@RequestParam("email")String email){
        return bookReviewRepository.selectBookReviewByEmail(email);
    }*/
}
