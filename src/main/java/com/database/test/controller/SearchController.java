package com.database.test.controller;


import com.database.test.entity.Book;
import com.database.test.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    BookRepository bookRepository;


    //搜索功能，可以找书名和作者，暂定两个
    /*@RequestMapping(value = "/search",method = RequestMethod.POST)
    public String searchFor(@RequestParam("searchMessage")String searchMessage,
                            Model model){
        List<Book> result=bookRepository.findByBookNameLike("%"+searchMessage+"%");
        if (result.size()==0){
            result=bookRepository.findByBookAuthorLike("%"+searchMessage+"%");
        }
        model.addAttribute("searchResult",result);
        return "searchPage";
    }*/
}
