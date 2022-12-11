package by.tc.task.dao;

import by.tc.task.dao.exception.DAOException;
import by.tc.task.entity.Student;
import by.tc.task.entity.User;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.rights.RightType;

import java.util.List;

public interface UsersDAO {
    RightType getRightType(String login, String hashedPassword) throws DAOException;
}
