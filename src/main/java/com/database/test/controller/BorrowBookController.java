package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.entity.User;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
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


    @RequestMapping(value = "/listBook",method = RequestMethod.GET)
    public String listBook(Model model,
                           HttpSession session){
        //List<Book> bookList=bookRepository.listAll();
        List<Book> bookList=bookRepository.listNotBorrowedBook();

        for (int i=0;i<bookList.size();i++){
            Book book=bookList.get(i);
            if (book.getBookIntroduction().length()>20){
                book.setBookIntroduction(book.getBookIntroduction().substring(0,20));
            }
        }
        System.out.println(session.getAttribute("currentEmail"));
        model.addAttribute("list",bookList);
        return "borrow";
    }


    @ResponseBody
    @RequestMapping(value = "/borrowBook",method = RequestMethod.POST)
    public boolean borrowBook(@RequestParam("bookId")Integer bookId,
                              HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        int result=bookRepository.updateBookReadingByBookId(bookId,email);
        System.out.println(result);
        return true;
    }





































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

}
