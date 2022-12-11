package by.tc.task.service.impl;

import by.tc.task.dao.ArchiveDAO;
import by.tc.task.dao.exception.DAOException;
import by.tc.task.dao.factory.DAOFactory;
import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.criteria.SearchCriteria;
import by.tc.task.entity.rights.RightType;
import by.tc.task.service.ArchiveService;
import by.tc.task.service.exception.ServiceException;
import by.tc.task.service.validation.Validator;

import java.util.List;

public class ArchiveServiceImpl implements ArchiveService {
    private DAOFactory factory;
    private ArchiveDAO archiveDAO;
    public ArchiveServiceImpl()
    {
        factory = DAOFactory.getInstance();
        archiveDAO = factory.getArchiveDAO();
    }
    @Override
    public List<Student> find(Criteria criteria, RightType rightType) throws ServiceException {
        if (!Validator.criteriaValidator(criteria)) {
            return null;
        }
        List<Student> students = null;
        try {
            if(rightType.compareTo(RightType.READ) >= 0) {
                students = archiveDAO.find(criteria);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return students;
    }

    @Override
    public void delete(Criteria criteria, RightType rightType) throws ServiceException {
        if (!Validator.criteriaValidator(criteria)) {
            return;
        }
        try{
            archiveDAO.delete(criteria);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void add(Student student, RightType rightType) throws ServiceException {
        try {
            if(rightType.compareTo(RightType.MODIFICATE) >= 0) {
                archiveDAO.add(student);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeStudentInfo(Criteria criteria, Criteria attributeToChange, RightType rightType) throws ServiceException {
        if (!Validator.criteriaValidator(criteria)) {
            return;
        }
        try {
            if(rightType.compareTo(RightType.MODIFICATE) >= 0) {
                archiveDAO.changeStudentInfo(criteria, attributeToChange);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
