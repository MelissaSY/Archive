package by.tc.task.dao.factory;

import by.tc.task.dao.ArchiveDAO;
import by.tc.task.dao.UsersDAO;
import by.tc.task.dao.exception.DAOException;
import by.tc.task.dao.impl.XmlArchiveDAO;
import by.tc.task.dao.impl.XmlUsersDAO;
import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;

import java.util.List;

public final class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private final ArchiveDAO archiveDAO = new XmlArchiveDAO();
    private final UsersDAO usersDAO = new XmlUsersDAO();
    private DAOFactory(){}
    public ArchiveDAO getArchiveDAO() { return archiveDAO; }
    public UsersDAO getUsersDAO() { return usersDAO; }
    public static DAOFactory getInstance() { return instance; }
}
