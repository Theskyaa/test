package com.database.test.controller;

import com.database.test.entity.GroupList;
import com.database.test.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    GroupRepository groupRepository;


    @RequestMapping(value = "/groupJoinIn",method = RequestMethod.POST)
    public String groupJoinIn(){


        return "group/groupJoinInPage";
    }


    @RequestMapping(value = "/groupEntry",method = RequestMethod.GET)
    public String groupEntry(HttpSession session,
                             Model model){
        String email= (String) session.getAttribute("currentEmail");
        System.out.println(email);
        List<GroupList> groupList=groupRepository.selectJoinInGroupName(email);
        model.addAttribute("groupList",groupList);
        System.out.println(groupList.get(0).getGroupId()+groupList.get(0).getGroupName());
        return "group/groupEntryPage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/groupEntrySuccess",method = RequestMethod.POST)
    public boolean groupEntrySuccess(@RequestParam("groupId")Integer groupId,
                                    HttpSession session){
        session.setAttribute("currentGroupId",groupId);
        System.out.println(groupId);
        return true;
    }
}
