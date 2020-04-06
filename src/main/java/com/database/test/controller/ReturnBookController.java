package com.database.test.controller;

import com.database.test.entity.Book;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.ReturnRecordsRepository;
import com.database.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReturnBookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReturnRecordsRepository returnRecordsRepository;


    @RequestMapping(value = "/returnBook",method = RequestMethod.GET)
    public String returnBook(Model model,
                             HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        List<Book> bookList=bookRepository.listBorrowedBookByEmail(email);
        for (int i=0;i<bookList.size();i++){
            if (bookList.get(i).getBookIntroduction().length()>20){
                bookList.get(i).setBookIntroduction(bookList.get(i).getBookIntroduction().substring(0,20));
            }
        }
        model.addAttribute("borrowedBookList",bookList);
        return "return";
    }

    @ResponseBody
    @RequestMapping(value = "/returnBookSuccess",method = RequestMethod.POST)
    public boolean returnBookSuccess(@RequestParam("bookId") Integer bookId,
                                     HttpSession session){
        System.out.println("bookid:"+bookId);
        int result=bookRepository.resetBookReading(bookId);
        System.out.println(result);
        return true;
    }

    /*@ResponseBody
    @RequestMapping(value = "/returnbook",method = RequestMethod.POST)
    public boolean returnBook(@RequestParam("bookId")String bookID,
                              @RequestParam("email")String email,
                              @RequestParam("book_borrow_time")String bookBorrowTime){


        //在BorrowingRecords表中记录下这条还书记录
        List<BorrowRecords> list=borrowRecordsRepository.findAll();
        int num=list.size();
        System.out.println(num);
        //borrowingRecordsRepository.insertRecords(3,email,bookID,bookBorrowTime,bookReturnTime);
        BorrowRecords borrowRecords=new BorrowRecords();
        borrowRecords.setBookID(bookID);
        borrowRecords.setEmail(email);
        borrowRecords.setBookBorrowTime(bookBorrowTime);
        borrowRecords.setRecordID(num);
        borrowRecordsRepository.save(borrowRecords);
        return true;
    }
*//*
    @ResponseBody
    @RequestMapping(value = "/returnbook",method = RequestMethod.POST)
    public boolean returnbook(@RequestBody Map<String,Object> map) {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

        String email= (String) map.get("email");
        //String email="admin@qq.com";
        System.out.println("start");
        System.out.println(email);
        List<Map<String,String>> bookArray= (List<Map<String, String>>) map.get("returnBook");
        System.out.println(bookArray.size());
        for (int i=0;i<bookArray.size();i++){
            //对book表的操作，将book_reading改为null
            String currentBookName=bookArray.get(i).get("book_name");
            Book currentBook=bookRepository.selectByBookName(currentBookName).get(0);
            int bookReadingFlag=bookRepository.updateBookReading(currentBook.getBookId(),"null");
            if (bookReadingFlag!=1){
                return false;
            }
            //修改returnRecord表
            int returnRecordNumber=returnRecordsRepository.listAllReturnRecords().size();
            int returnRecordFlag=returnRecordsRepository.insertReturnRecord(returnRecordNumber+1,simpleDateFormat.format(calendar.getTime()));
            if (returnRecordFlag!=1){
                return false;
            }
            //修改borrowRecord表
            int borrowRecordFlag=borrowRecordsRepository.updateBorrowRecord(email,returnRecordNumber+1,currentBook.getBookId());
            if (borrowRecordFlag!=1){
                return false;
            }
        }

        return true;

    }

    @ResponseBody
    @RequestMapping(value = "/listBorrowingBook",method = RequestMethod.POST)
    public Map<String,Object> listBorrowingBook(@RequestParam("email")String email){
        List<Map<String,Object>> resultData=borrowRecordsRepository.selectByEmailAndReturnId(email,0);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("bookInfo",resultData);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "listBorrowRecordByEmail",method = RequestMethod.POST)
    public List<Map<String,Object>> listBorrowRecordByEmail(@RequestParam("email")String email){

        return borrowRecordsRepository.selectRecordByEmail(email);
    }

    *//*@ResponseBody
    @RequestMapping(value = "/returnBook",method = RequestMethod.POST)
    public boolean returnBook(@RequestParam("bookName")String bookName,
                              @RequestParam("email")String email) {
        Book currentBook = bookRepository.selectByBookName(bookName).get(0);
        User currentReadingUser = userRepository.selectByEmail(email);
        if (currentBook.getBookCurrentReading().equals(currentReadingUser.getUsername())) {
            return true;
        }

        return true;

    }*//*

    *//*@ResponseBody
    @RequestMapping(value = "/returnBookRecords",method = RequestMethod.POST)
    public List<Object> returnBookRecords(@RequestParam("email")String email){
        return borrowRecordsRepository.selectBorrowRecords(email);

        //return borrowingRecordsRepository.findByEmail(email);
    }*/
    
}
