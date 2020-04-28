package com.database.test.controller;


import com.database.test.entity.Book;
import com.database.test.entity.BookReview;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @RequestMapping(value = "/bookUpload",method = RequestMethod.GET)
    public String bookUpload(){
        return "book/bookUploadPage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/bookUploadSuccess",method = RequestMethod.POST)
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

    @ResponseBody
    @RequestMapping(value = "/bookId",method = RequestMethod.POST)
    public boolean postBookId(@RequestParam("bookId")Integer bookId,
                             HttpSession session){
        session.setAttribute("currentBookId",bookId);
        return true;
    }


    @RequestMapping(value = "/bookDetail",method = RequestMethod.GET)
    public String bookDetail(HttpSession session,
                             Model model){
        Integer bookId= (Integer) session.getAttribute("currentBookId");
        Book book=bookRepository.selectByBookId(bookId);
        model.addAttribute("bookId",book.getBookId());
        model.addAttribute("bookName",book.getBookName());
        model.addAttribute("bookAuthor",book.getBookAuthor());
        model.addAttribute("bookOwner",book.getBookOwner());
        model.addAttribute("bookPublishingHouse",book.getBookPublishingHouse());
        model.addAttribute("bookScore",book.getBookScore());
        model.addAttribute("bookIntroduction",book.getBookIntroduction());

        List<Map<String,Object>> reviewList=bookReviewRepository.selectBookReviewByBookId(bookId);
        model.addAttribute("reviewList",reviewList);
        return "book/bookDetailPage.html";
    }



    @RequestMapping(value = "/bookInfoManage",method = RequestMethod.GET)
    public String bookInfoManage(HttpSession session,
                                 Model model){
        Integer bookId= (Integer) session.getAttribute("currentBookId");
        Book book=bookRepository.selectByBookId(bookId);
        model.addAttribute("bookId",book.getBookId());
        model.addAttribute("bookName",book.getBookName());
        model.addAttribute("bookAuthor",book.getBookAuthor());
        model.addAttribute("bookOwner",book.getBookOwner());
        model.addAttribute("bookPublishingHouse",book.getBookPublishingHouse());
        model.addAttribute("bookScore",book.getBookScore());
        model.addAttribute("bookIntroduction",book.getBookIntroduction());
        List<Map<String,Object>> reviewList=bookReviewRepository.selectBookReviewByBookId(bookId);
        model.addAttribute("reviewList",reviewList);
        return "book/bookInfoManagePage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/bookInfoModify",method = RequestMethod.POST)
    public boolean bookInfoModify(@RequestParam("bookId")Integer bookId,
                                  @RequestParam("bookName")String bookName,
                                  @RequestParam("bookAuthor")String bookAuthor,
                                  @RequestParam("bookPublishingHouse")String bookPublishingHouse,
                                  @RequestParam("bookScore")Integer bookScore,
                                  @RequestParam("bookIntroduction")String bookIntroduction){

        //System.out.println(bookIntroduction);
        bookRepository.updateBookInfo(bookId,bookName,bookAuthor,bookPublishingHouse,bookIntroduction);
        return true;
    }

}
