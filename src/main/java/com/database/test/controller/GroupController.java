package com.database.test.controller;

import com.database.test.entity.GroupList;
import com.database.test.repository.GroupRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    GroupRepository groupRepository;


    @RequestMapping(value = "/groupJoinIn",method = RequestMethod.GET)
    public String groupJoinIn(HttpSession session,
                              Model model){
        String email= (String) session.getAttribute("currentEmail");
        List<GroupList> groupLists=groupRepository.selectNotJoinInGroupByEmial(email);
        for (int i=0;i<groupLists.size();i++){
            if (groupLists.get(i).getGroupIntroduction().length()>20){
                groupLists.get(i).setGroupIntroduction(groupLists.get(i).getGroupIntroduction().substring(0,19));
            }
        }
        model.addAttribute("groupList",groupLists);


        //show detail
        for (int i=0;i<groupLists.size();i++){
            System.out.println(groupLists.get(i).getGroupId());
            System.out.println(groupLists.get(i).getGroupName());
        }
        return "group/groupJoinInPage.html";
    }

    @ResponseBody
    @RequestMapping(value = "/groupJoinInSuccess",method = RequestMethod.POST)
    public boolean groupJoinInSuccess(@RequestParam("groupId")Integer groupId,
                                      HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Integer result=groupRepository.insertUserBelong(email,groupId);
        System.out.println("insert result:"+result);
        return true;
    }


    @RequestMapping(value = "/groupEntry",method = RequestMethod.GET)
    public String groupEntry(HttpSession session,
                             Model model){
        String email= (String) session.getAttribute("currentEmail");
        System.out.println(email);
        List<GroupList> groupList=groupRepository.selectJoinInGroup(email);
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


    @RequestMapping(value = "/groupManage",method = RequestMethod.GET)
    public String groupManage(HttpSession session,
                              Model model){
        String email= (String) session.getAttribute("currentEmail");
        List<GroupList> groupList=groupRepository.selectJoinInGroup(email);
        model.addAttribute("groupList",groupList);
        return "group/groupManagePage.html";
    }

    @ResponseBody
    @RequestMapping(value = "/groupManageSuccess",method = RequestMethod.POST)
    public boolean groupManageSuccess(@RequestParam("groupId")Integer groupId,
                                      HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        int result=groupRepository.deleteByGroupIdAndEmail(email,groupId);
        System.out.println("delete result: "+result);
        return true;
    }

    @RequestMapping(value = "/groupCreate",method = RequestMethod.GET)
    public String groupCreate(){
        return "group/groupCreatePage.html";
    }


    @ResponseBody
    @RequestMapping(value = "/groupCreateSuccess",method = RequestMethod.POST)
    public boolean groupCreateSuccess(@RequestParam("groupName")String groupName,
                                     @RequestParam("groupIntroduction")String groupIntroduction,
                                     HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String time=simpleDateFormat.format(calendar.getTime());
        GroupList groupList=groupRepository.selectMaxGroupId();
        Integer maxId=groupList.getGroupId();
        int result=groupRepository.insertGroupListRecord(maxId+1,groupName,email,time,groupIntroduction,0);
        System.out.println("create group: "+result);
        return true;
    }
}
