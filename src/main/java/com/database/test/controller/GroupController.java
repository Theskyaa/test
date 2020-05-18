package com.database.test.controller;

import com.database.test.entity.GroupList;
import com.database.test.repository.BookRepository;
import com.database.test.repository.BookReviewRepository;
import com.database.test.repository.BorrowRecordsRepository;
import com.database.test.repository.GroupRepository;
import com.database.test.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowRecordsRepository borrowRecordsRepository;

    @Autowired
    BookReviewRepository bookReviewRepository;


    //小组加入
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
        return "group/groupJoinInPage.html";
    }
    @ResponseBody
    @RequestMapping(value = "/groupJoinInSuccess",method = RequestMethod.POST)
    public boolean groupJoinInSuccess(@RequestParam("groupId")Integer groupId,
                                      HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        Integer result=groupRepository.insertUserBelong(email,groupId);
        GroupList currentGroup=groupRepository.selectByGroupId(groupId);
        groupRepository.updateGroupNumber(currentGroup.getGroupNumber()+1,groupId);
        return true;
    }


    //小组选择
    @RequestMapping(value = "/groupEntry",method = RequestMethod.GET)
    public String groupEntry(HttpSession session,
                             Model model){
        String email= (String) session.getAttribute("currentEmail");
        System.out.println(email);
        List<GroupList> groupList=groupRepository.selectJoinInGroup(email);
        model.addAttribute("groupList",groupList);
        return "group/groupEntryPage.html";
    }
    @ResponseBody
    @RequestMapping(value = "/groupEntrySuccess",method = RequestMethod.POST)
    public boolean groupEntrySuccess(@RequestParam("groupId")Integer groupId,
                                    HttpSession session){
        session.setAttribute("currentGroupId",groupId);
        String groupName=groupRepository.selectByGroupId(groupId).getGroupName();
        session.setAttribute("currentGroupName",groupName);
        return true;
    }


    //小组退出
    @RequestMapping(value = "/groupQuit",method = RequestMethod.GET)
    public String groupQuit(HttpSession session,
                              Model model){
        String email= (String) session.getAttribute("currentEmail");
        List<GroupList> groupList=groupRepository.selectJoinInGroup(email);
        model.addAttribute("groupList",groupList);
        return "group/groupQuitPage.html";
    }
    @ResponseBody
    @RequestMapping(value = "/groupQuitSuccess",method = RequestMethod.POST)
    public boolean groupQuitSuccess(@RequestParam("groupId")Integer groupId,
                                      HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        int result=groupRepository.deleteByGroupIdAndEmail(email,groupId);
        GroupList groupList=groupRepository.selectByGroupId(groupId);
        groupRepository.updateGroupNumber(groupList.getGroupNumber()-1,groupId);
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
        List<GroupList> groupList=groupRepository.selectMaxGroupId();
        int maxId=0;
        if (groupList.size()==0){
            maxId=0;
        }else {
            maxId=groupList.get(0).getGroupId();
        }
        int result=groupRepository.insertGroupListRecord(maxId+1,groupName,email,new TimeUtil().getCurrentTime(),groupIntroduction,0);
        return true;
    }


    //小组管理
    @RequestMapping(value = "/groupManage",method = RequestMethod.GET)
    public String groupManage(HttpSession session,
                              Model model){

        String email= (String) session.getAttribute("currentEmail");
        List<GroupList> groupLists=groupRepository.selectGroupByGroupFounder(email);
        model.addAttribute("groupList",groupLists);
        return "group/groupManagePage.html";
    }
    @ResponseBody
    @RequestMapping(value = "/groupManageSuccess",method = RequestMethod.POST)
    public boolean groupManageSuccess(@RequestParam("groupId")Integer groupId,
                                      HttpSession session){
        String email= (String) session.getAttribute("currentEmail");
        //该操作涉及删除多张表的记录，需要进行多次测试，可能会有一些问题存在

        //删除该小组书库中所有书的Review记录
        bookReviewRepository.deleteByBookIdFromBookBelong(groupId);
        //删除group表中的内容
        groupRepository.deleteByGroupIdFromGroupList(groupId);
        //通过bookbelong,borrow来通过groupId删除该书库的借阅记录
        borrowRecordsRepository.deleteByGroupIdFromBorrowRecord(groupId);
        //通过bookbelong表删除Book表中该小组书库中的所有书
        bookRepository.deleteByGroupIdFromBookAndBookBelong(groupId);
        //删除bookbelong表中记录
        groupRepository.deleteByGroupIdFromBookBelong(groupId);
        //删除UserBelong表中的内容
        groupRepository.deleteByGroupIdFromUserBelong(groupId);
        return true;
    }
    //小组信息修改
    @ResponseBody
    @RequestMapping(value = "/chooseGroup",method = RequestMethod.POST)
    public boolean chooseGroup(@RequestParam("groupId")Integer groupId,
                               HttpSession session){
        session.setAttribute("modifyGroupID",groupId);
        return true;
    }
    @RequestMapping(value = "/groupInfoManage",method = RequestMethod.GET)
    public String getGroupInfoManage(HttpSession session,
                               Model model){
        Integer groupId= (Integer) session.getAttribute("modifyGroupID");
        GroupList group=groupRepository.selectByGroupId(groupId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",group.getGroupName());
        model.addAttribute("groupFounder",group.getGroupFounder());
        model.addAttribute("groupFoundingTime",group.getGroupFoundingTime());
        model.addAttribute("groupNumber",group.getGroupNumber());
        model.addAttribute("groupIntroduction",group.getGroupIntroduction());
        return "group/groupInfoManagePage.html";
    }
    @ResponseBody
    @RequestMapping(value = "/groupInfoModify",method = RequestMethod.POST)
    public boolean groupInfoModify(@RequestParam("groupId")Integer groupId,
                                   @RequestParam("groupName")String groupName,
                                   @RequestParam("groupIntroduction")String groupIntroduction){
        groupRepository.updateGroupInfo(groupId,groupName,groupIntroduction);
        return true;
    }

    //小组信息的detail显示
    @RequestMapping(value = "/groupDetail",method = RequestMethod.GET)
    public String getGroupInfoDetail(HttpSession session,
                                     Model model){
        Integer groupId= (Integer) session.getAttribute("modifyGroupID");
        GroupList group=groupRepository.selectByGroupId(groupId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",group.getGroupName());
        model.addAttribute("groupFounder",group.getGroupFounder());
        model.addAttribute("groupFoundingTime",group.getGroupFoundingTime());
        model.addAttribute("groupNumber",group.getGroupNumber());
        model.addAttribute("groupIntroduction",group.getGroupIntroduction());
        return "group/groupInfoPage.html";
    }
}
