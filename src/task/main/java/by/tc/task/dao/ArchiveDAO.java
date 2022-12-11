package by.tc.task.dao;

import by.tc.task.dao.exception.DAOException;
import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.criteria.SearchCriteria;
import by.tc.task.service.exception.ServiceException;

import java.util.List;

public interface ArchiveDAO {
    List<Student> find(Criteria criteria) throws DAOException;
    void delete(Criteria criteria) throws DAOException;
    void add(Student student) throws DAOException;
    void changeStudentInfo(Criteria criteria, Criteria attributeToChange) throws DAOException;

}
