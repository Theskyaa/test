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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/bookUpload",method = RequestMethod.GET)
    public String bookUpload(){
        return "book/bookUploadPage.html";
    }


    @ResponseBody
    @RequestMapping(value = "bookUploadSuccess",method = RequestMethod.POST)
    public boolean bookUploadSuccess(@RequestParam("bookName")String bookName,
                                     @RequestParam("bookAuthor")String bookAuthor,
                                     @RequestParam("bookPublishingHouse")String bookPublishingHouse,
                                     @RequestParam("bookIntroduction")String bookIntroduction,
                                     @RequestParam("bookScore")Integer bookScore,
                                     HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Integer groupId= (Integer) session.getAttribute("currentGroupId");

        int maxBookId=0;
        List<Book> bookList =bookRepository.selectMaxBookId();
        if (bookList.size()==0){
            maxBookId=0;
        }else {
            maxBookId=bookList.get(0).getBookId();
        }
        bookRepository.insertBook(maxBookId+1,bookName,bookAuthor,email,"null",bookPublishingHouse,bookScore,bookIntroduction);
        bookRepository.insertBookBelong(maxBookId+1,groupId);
        return true;
    }


    @RequestMapping(value = "/bookManage",method = RequestMethod.GET)
    public String bookManage(HttpSession session,
                             Model model){
        String email= (String) session.getAttribute("currentEmail");
        Integer groupId= (Integer) session.getAttribute("currentGroupId");
        List<Book> bookList=bookRepository.selectBookByBookOwnerAndGroupId(email,groupId);
        model.addAttribute("bookList",bookList);
        return "book/bookManagePage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/bookManageSuccess",method = RequestMethod.POST)
    public boolean bookManageSuccess(@RequestParam("bookId")Integer bookId,
                                     HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Integer groupId= (Integer) session.getAttribute("currentGroupId");

        //考虑到当前要删除的书籍可能正在被他人借阅，因此要进行判断
        int result=bookRepository.deleteBookByBookID(bookId);
        if (result==1){
            bookRepository.deleteBookBelongByBookIdAndGroupId(bookId,groupId);
            return true;
        }else {
            return false;
        }
    }

}
