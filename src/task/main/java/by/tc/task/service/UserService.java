package by.tc.task.service;
import by.tc.task.entity.rights.RightType;
import by.tc.task.service.exception.ServiceException;

public interface UserService {

    RightType getRightType(String login, String hashedPassword) throws ServiceException;
}
