package com.database.test.controller;


import com.database.test.entity.Book;
import com.database.test.entity.GroupList;
import com.database.test.repository.BookRepository;
import com.database.test.repository.GroupRepository;
import com.database.test.util.SubTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GroupRepository groupRepository;


    //书籍搜索功能，可以按照书名和作者名两个条件模糊查找
    @RequestMapping(value = "/searchBook",method = RequestMethod.POST)
    public String searchBook(@RequestParam("searchMessage")String searchMessage,
                             Model model){

        List<Book> bookList=bookRepository.selectBookLikeBookName(searchMessage);
        if (bookList.size()==0){
            bookList=bookRepository.selectBookLikeBookAuthor(searchMessage);
        }

        //简介缩写20字，优化排版
        new SubTextUtil().subBookIntroduction(bookList);

        model.addAttribute("list",bookList);
        return "book/bookBorrowPage.html";
    }


    @RequestMapping(value = "/groupSearch",method = RequestMethod.POST)
    public String groupSearch(@RequestParam("groupName")String groupName,
                              HttpSession session,
                              Model model){
        String email= (String) session.getAttribute("currentEmail");
        List<GroupList> groupLists=groupRepository.selectGroupLike(groupName,email);
        model.addAttribute("groupList",groupLists);
        return "group/groupJoinInPage.html";
    }
}
