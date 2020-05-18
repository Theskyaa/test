package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.util.SubTextUtil;
import com.database.test.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ReturnBookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;


    @RequestMapping(value = "/returnBook",method = RequestMethod.GET)
    public String returnBook(Model model,
                             HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Integer groupId= (Integer) session.getAttribute("currentGroupId");
        List<Book> bookList=bookRepository.listBorrowedBookByEmailAndGroupId(groupId,email);

        //简介缩写20字的长度，方便显示
        new SubTextUtil().subBookIntroduction(bookList);

        model.addAttribute("borrowedBookList",bookList);
        return "book/bookReturnPage.html";
    }

    @ResponseBody
    @RequestMapping(value = "/returnBookSuccess",method = RequestMethod.POST)
    public boolean returnBookSuccess(@RequestParam("bookId") Integer bookId,
                                     HttpSession session){
        System.out.println("bookid:"+bookId);
        int result=bookRepository.resetBookReading(bookId);
        borrowRecordsRepository.updateReturnTimeByBookId(bookId,new TimeUtil().getCurrentTime());
        System.out.println(result);
        return true;
    }
}
