package com.shenl.project.controller;

import com.shenl.project.bean.Subject;
import com.shenl.project.respository.SubjectRepository;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubjectConteroller {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping(value = "/getSubjectList")
    public Map<String,Object> getSubjectList(){
        return Global.getMap(subjectRepository.findAll());
    }

    @GetMapping(value = "/getTypeSubject")
    public Map<String,Object> getTypeSubject(@RequestParam("subjectType") String subjectType){
        return Global.getMap(subjectRepository.findBySubjectType(subjectType));
    }

    @PostMapping(value = "/AddSubject")
    public Map<String,Object> AddSubject(@RequestParam("subjectName") String subjectName,
                                         @RequestParam("subjectType") String subjectType,
                                         @RequestParam("lecturer") String lecturer,
                                         @RequestParam("author") String author,
                                         @RequestParam("subjectContent") String subjectContent,
                                         @RequestParam("createUserId") String createUserId,
                                         @RequestParam("createUserName") String createUserName){
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        subject.setSubjectType(subjectType);
        subject.setLecturer(lecturer);
        subject.setAuthor(author);
        subject.setSubjectContent(subjectContent);
        subject.setCreateUserId(createUserId);
        subject.setCreateUserName(createUserName);
        return Global.getMap(subjectRepository.save(subject));
    }

    @GetMapping(value = "/getUserSubject")
    public Map<String,Object> getUserSubject(@RequestParam("createUserId") String createUserId){
        return Global.getMap(subjectRepository.findByCreateUserId(createUserId));
    }

    @GetMapping(value = "/DelSubject")
    public Map<String,Object> DelSubject(@RequestParam("id") Integer id){
        subjectRepository.deleteById(id);
        return Global.getMap(null,"删除成功");
    }
}
