package by.tc.task.main.requests;

import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.rights.RightType;
import by.tc.task.service.ArchiveService;
import by.tc.task.service.UserService;
import by.tc.task.service.exception.ServiceException;
import by.tc.task.service.impl.ArchiveServiceImpl;
import by.tc.task.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Objects;

public class UserClient {
    private ArchiveService archiveService = new ArchiveServiceImpl();
    private RightType rights = null;
    private Requests.Request request = null;
    private Criteria criteria;
    private Criteria criteriaChange;
    private UserService userService = new UserServiceImpl();
    private String login;
    private String hashedPassword;
    private String[] rawParameters;
    private Student student = null;
    public void parseRequest(String request) throws ServiceException {
        rawParameters = request.split(" ");
        this.request = Requests.getRequest(rawParameters[0]);

        if(rawParameters.length > 1)
        {
            String searchParameters = rawParameters[1];
            switch (Objects.requireNonNull(this.request)) {
                case LOGIN:
                    if(rawParameters.length > 2)
                    {
                        login = rawParameters[1];
                        hashedPassword = rawParameters[2];
                    }
                    break;
                case DELETE:
                    if(rights != null && rights.compareTo(RightType.DELETE) >= 0) {
                        criteria = getCriteria(searchParameters);
                    }
                    break;
                case READ:
                    if(rights != null && rights.compareTo(RightType.READ) >= 0) {
                        criteria = getCriteria(searchParameters);
                    }
                    break;
                case ADD:
                    if(rights != null && rights.compareTo(RightType.MODIFICATE) >= 0) {
                        criteria = getCriteria(searchParameters);
                        student = getStudentFromCriteria(criteria);
                    }
                    break;
                case MODIFICATE:
                    if(rights != null && rights.compareTo(RightType.MODIFICATE) >= 0) {
                        String[] parameters = searchParameters.split(";");
                        criteriaChange = getCriteria(parameters[1]);
                        criteria = getCriteria(parameters[0]);
                    }
                    break;
                case EXIT:
                    break;
            }
        }
    }
    public String processRequest() throws ServiceException {
        String res = "error";
        try {
            if(request != null) {
                switch (request) {
                    case READ:
                        if(rights == null)
                        {
                            res = "login first";
                        } else if (rights.compareTo(RightType.READ) >= 0) {
                            List<Student> students = archiveService.find(criteria, rights);
                            res = "";
                            for (Student student:
                                    students) {
                                res += student.toString() + " ";
                            }
                        } else  {
                            res = "not enough rights";
                        }
                        break;
                    case LOGIN:
                        if(rights != null) {
                            res = "exit first";
                        } else  {
                            rights = userService.getRightType(login, hashedPassword);
                            res = rights != null ? "success" : "no user found";
                        }
                        break;
                    case DELETE:
                        if(rights == null) {
                            res ="login first";
                        } else if (rights.compareTo(RightType.DELETE) >= 0) {
                            archiveService.delete(criteria, rights);
                            res = "success";
                        } else {
                            res ="not enough rights";
                        }
                        break;
                    case MODIFICATE:
                        if(rights == null) {
                            res ="login first";
                        } else if(rights.compareTo(RightType.MODIFICATE) >= 0) {
                            archiveService.changeStudentInfo(criteria, criteriaChange, rights);
                            res = "success";
                        } else {
                            res ="not enough rights";
                        }
                        break;
                    case ADD:
                        if(rights == null) {
                            res ="login first";
                        } else if(rights.compareTo(RightType.MODIFICATE) >= 0) {
                            archiveService.add(student, rights);
                            res = "success";
                        } else {
                            res ="not enough rights";
                        }
                        break;
                    case EXIT:
                        if(rights != null) {
                            rights = null;
                            criteria = null;
                            criteriaChange = null;
                            student = null;
                        } else {
                            res = "login first";
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return res;
    }
    public String[] getRawParameters() {
        return rawParameters;
    }
    private Criteria getCriteria(String requestString) {
        Criteria criteria = new Criteria("Student");
        String[] criteriaAndValues = requestString.split(",");
        for (String criteriaRequest:
                criteriaAndValues) {
            String[] singleCriteria = criteriaRequest.split("=");
            if(singleCriteria.length > 1)
            {
                String criteriaName = singleCriteria[0];
                String criteriaValue = singleCriteria[1];
                criteria.add(criteriaName, criteriaValue);
            }
        }
        if(requestString.equals("all"))
        { criteria.add("all", "all"); }
        return criteria;
    }
    private Student getStudentFromCriteria(Criteria criteria) throws ServiceException {
        Student student = null;
        try {
        int id = Integer.parseInt(criteria.get("id").toString());
        int groupNumber = Integer.parseInt(criteria.get("group").toString());
        String name = criteria.get("name").toString();
        String surname = criteria.get("surname").toString();
        student = new Student(name, surname, groupNumber, id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return student;
    }
    public Requests.Request getRequest() {
        return request;
    }
}
