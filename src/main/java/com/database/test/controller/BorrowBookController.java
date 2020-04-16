package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.entity.BorrowRecords;
import com.database.test.entity.User;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.UserRepository;
import com.database.test.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BorrowBookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;


    @RequestMapping(value = "/listBook",method = RequestMethod.GET)
    public String listBook(Model model,
                           HttpSession session){
        //List<Book> bookList=bookRepository.listAll();
        Integer groupId= (Integer) session.getAttribute("currentGroupId");
        List<Book> bookList=bookRepository.listNotBorrowedBookByGroupId(groupId);

        for (int i=0;i<bookList.size();i++){
            Book book=bookList.get(i);
            if (book.getBookIntroduction().length()>20){
                book.setBookIntroduction(book.getBookIntroduction().substring(0,20));
            }
        }
        System.out.println(session.getAttribute("currentEmail"));
        model.addAttribute("list",bookList);
        return "book/bookBorrowPage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/borrowBook",method = RequestMethod.POST)
    public boolean borrowBook(@RequestParam("bookId")Integer bookId,
                              HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        int result=bookRepository.updateBookReadingByBookId(bookId,email);
        List<BorrowRecords> records=borrowRecordsRepository.selectMaxRecordId();

        //生成借阅record
        int maxId=0;
        if (records.size()==0){
            maxId=0;
        }else {
            maxId=records.get(0).getRecordId();
        }
        borrowRecordsRepository.insertRecord(maxId+1,email,bookId,new TimeUtil().getCurrentTime(),"null");
        return true;
    }
}
