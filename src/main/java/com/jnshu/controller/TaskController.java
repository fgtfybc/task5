package com.jnshu.controller;

import com.jnshu.pojo.Position;
import com.jnshu.pojo.Student;
import com.jnshu.service.PositionService;
import com.jnshu.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Administrator
 */
@Controller
public class TaskController {
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "student",method = RequestMethod.GET)
    public String getIndex(Model model){
        //查询优秀学员
        List<Student> studentList = studentService.selectStudent();

        System.out.println(studentList);
        //累计在线学习人数
        Integer count = studentService.selectByLearning();
        //已找到工作的学员
        Integer number = studentService.selectByWorking();
        model.addAttribute("studentList",studentList);
        model.addAttribute("count",count);
        model.addAttribute("number",number);
        return "student";
    }

    @RequestMapping(value = "profession",method = RequestMethod.GET)
    public String getProfession(Model model){
        List<Position> professionsList = positionService.selectPosition();
        model.addAttribute("profession",professionsList);
        return "profession";
    }

    @RequestMapping("/test")
    public String testView(){
        return "myView";
    }
}

