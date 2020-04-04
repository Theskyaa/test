package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.entity.User;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BorrowBookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;

    //没有界面
    @ResponseBody
    @RequestMapping(value = "/listAllBook",method = RequestMethod.POST)
    public List<Book> listAll(){

        return bookRepository.listAll();
    }

    @ResponseBody
    @RequestMapping(value = "/listBook",method = RequestMethod.POST)
    public Map<String, Object> selectBook(){
        List<Map<String,Object>> resultData=bookRepository.selectAllUsefulMessage();
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("bookInfo",resultData);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "/findBook",method = RequestMethod.POST)
    public List<Book> findBook(@RequestParam("bookName")String bookName){
        return bookRepository.findByBookName(bookName);
    }


    @ResponseBody
    @RequestMapping(value = "/postBooklist",method = RequestMethod.POST)
    public boolean borrow(@RequestBody Map<String,Object> map){
        //Book current=libraryItemRepository.listByBookName(bookName).get(0);
        //Book currentBook=bookRepository.listByBookId(BookID).get(0);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

        List<Map<String,String>> bookArray= (List<Map<String, String>>) map.get("shopCar");
        String email= (String) map.get("email");
        User user=userRepository.findByEmail(email).get(0);
        for (int i=0;i<bookArray.size();i++){
            String bookName=bookArray.get(i).get("book_name");
            System.out.println(bookName);
            Book currentBook=bookRepository.selectByBookName(bookName).get(0);
            int borrowRecordNumber=borrowRecordsRepository.listAllBorrowRecord().size()+1;
            if (currentBook.getBookCurrentReading().equals("null")){
                int bookFlag=bookRepository.updateBookReading(currentBook.getBookId(),user.getUsername());
                int borrowRecordFlag=borrowRecordsRepository.insertBorrowRecords(borrowRecordNumber,email,Integer.parseInt(currentBook.getBookId()),simpleDateFormat.format(calendar.getTime()),0);
                if (bookFlag!=1||borrowRecordFlag!=1){
                    return false;
                }
            }
        }

        return true;
    }

    /*@ResponseBody
    @RequestMapping(value = "/listBorrowAndReturnRecords",method = RequestMethod.POST)
    public Map<String,Object> listBookRecords(@RequestParam("email")String email){

        List<Map<String,Object>> resultData=bookRepository.selectRecords(email);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("recordInfo",resultData);
        return resultMap;
    }*/
}
