package com.database.test.controller;


import com.database.test.repository.BorrowRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class BorrowRecordController {

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;

    @RequestMapping(value = "/borrowRecord",method = RequestMethod.GET)
    public String getBorrowRecord(HttpSession session,
                                  Model model){
        Integer groupId= (Integer) session.getAttribute("currentGroupId");
        String email= (String) session.getAttribute("currentEmail");
        List<Map<String,Object>> borrowRecords=borrowRecordsRepository.selectBorrowRecordsByEmailAndGroupId(email,groupId);
        model.addAttribute("borrowRecords",borrowRecords);
        System.out.println("borrowRecordSize:"+borrowRecords.size());
        return "book/borrowRecordPage.html";
    }
}
