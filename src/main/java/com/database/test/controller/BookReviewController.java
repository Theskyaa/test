package com.database.test.controller;


import com.database.test.entity.Book;
import com.database.test.entity.BookReview;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BookReviewRepository;
import com.database.test.util.SubTextUtil;
import com.database.test.util.TimeUtil;
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
public class BookReviewController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;

    @RequestMapping(value = "/bookReview",method = RequestMethod.GET)
    public String bookReview(HttpSession session,
                             Model model){
        String email= (String) session.getAttribute("currentEmail");
        Integer groupId= (Integer) session.getAttribute("currentGroupId");
        List<Book> bookList=bookRepository.selectBookByGroupId(groupId);

        new SubTextUtil().subBookIntroduction(bookList);

        model.addAttribute("bookList",bookList);
        return "book/bookReviewPage.html";
    }

    @ResponseBody
    @RequestMapping(value = "/bookReviewSuccess",method = RequestMethod.POST)
    public boolean bookReviewSuccess(@RequestParam("bookId")Integer bookId,
                                     HttpSession session){
        session.setAttribute("currentBook",bookId);
        return true;
    }

    @RequestMapping(value = "/makeReview",method = RequestMethod.GET)
    public String makeReview(){
        return "book/makeReviewPage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/submitReview",method = RequestMethod.POST)
    public boolean submitReview(@RequestParam("reviewStar")Integer reviewStar,
                                @RequestParam("reviewText")String reviewText,
                                HttpSession session){
        Integer bookId= (Integer) session.getAttribute("currentBook");
        String email= (String) session.getAttribute("currentEmail");
        List<BookReview> bookReviews=bookReviewRepository.selectMaxReviewId();
        int maxId=0;
        if (bookReviews.size()==0){
            maxId=0;
        }else {
            maxId=bookReviews.get(0).getReviewID();
        }
        bookReviewRepository.insertReviewRecord(maxId+1,new TimeUtil().getCurrentTime(),reviewText,reviewStar,email,bookId);
        return true;
    }


    @ResponseBody
    @RequestMapping(value = "deleteBookReview",method = RequestMethod.POST)
    public boolean deleteBookReview(@RequestParam("reviewId")Integer reviewId){
        bookReviewRepository.deleteByReviewId(reviewId);
        return true;
    }
}
