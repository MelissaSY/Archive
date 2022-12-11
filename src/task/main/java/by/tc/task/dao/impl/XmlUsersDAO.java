package by.tc.task.dao.impl;

import by.tc.task.dao.UsersDAO;
import by.tc.task.dao.exception.DAOException;
import by.tc.task.entity.Student;
import by.tc.task.entity.User;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.rights.RightType;
import by.tc.task.entity.rights.RightTypeTranslator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class XmlUsersDAO implements UsersDAO {
    @Override
    public RightType getRightType(String login, String hashedPassword) throws DAOException {
        RightType rightType = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("src/task/main/resources/users.xml"));

            NodeList nodeUsers = document.getElementsByTagName("User");
            boolean isValid = false;
            int userNum = 0;
            while(!isValid && userNum < nodeUsers.getLength()) {
                isValid = ((Element) nodeUsers.item(userNum)).getAttribute("login").equals(login);
                isValid &= ((Element)nodeUsers.item(userNum)).getAttribute("hashedPassword").equals(hashedPassword);
                userNum++;
            }
            if (isValid) {
                userNum--;
                String stringRights = ((Element)nodeUsers.item(userNum)).getAttribute("rights");
                rightType = RightTypeTranslator.stringToRightType(stringRights);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DAOException();
        }
        return rightType;
    }
}
