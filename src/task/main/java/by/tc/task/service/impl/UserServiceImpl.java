package by.tc.task.service.impl;

import by.tc.task.dao.UsersDAO;
import by.tc.task.dao.exception.DAOException;
import by.tc.task.dao.factory.DAOFactory;
import by.tc.task.entity.rights.RightType;
import by.tc.task.entity.rights.RightTypeTranslator;
import by.tc.task.service.UserService;
import by.tc.task.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
    private DAOFactory factory;
    private UsersDAO usersDAO;
    public UserServiceImpl() {
        factory = DAOFactory.getInstance();
        usersDAO = factory.getUsersDAO();
    }
    @Override
    public RightType getRightType(String login, String hashedPassword) throws ServiceException {
        RightType rightType;
        try {
            rightType = usersDAO.getRightType(login, hashedPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return rightType;
    }
}
