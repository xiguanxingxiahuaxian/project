package com.shenl.project.respository;

import com.shenl.project.bean.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    List<Subject> findBySubjectType(String SubjectType);
    List<Subject> findByCreateUserId(String createUserId);
    void deleteById(Integer id);
}
