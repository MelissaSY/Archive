package by.tc.task.service;

import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.criteria.SearchCriteria;
import by.tc.task.entity.rights.RightType;
import by.tc.task.service.exception.ServiceException;

import java.util.List;

public interface ArchiveService {
    List<Student> find(Criteria criteria, RightType rightType) throws ServiceException;
    void delete(Criteria criteria, RightType rightType) throws ServiceException;
    void add(Student student, RightType rightType) throws ServiceException;
    void changeStudentInfo(Criteria criteria, Criteria attributeToChange, RightType rightType) throws ServiceException;
}
