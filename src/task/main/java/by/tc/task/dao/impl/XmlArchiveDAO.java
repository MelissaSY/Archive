package by.tc.task.dao.impl;

import by.tc.task.dao.ArchiveDAO;
import by.tc.task.dao.exception.DAOException;
import by.tc.task.entity.Student;
import by.tc.task.entity.criteria.Criteria;
import by.tc.task.entity.criteria.CriteriaTranslator;
import by.tc.task.entity.criteria.SearchCriteria;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;

public class XmlArchiveDAO implements ArchiveDAO {
    public XmlArchiveDAO()
    {    }
    @Override
    public List<Student> find(Criteria criteria) throws DAOException {
        List<Student> students;
        try {
            File resources = new File("src/task/main/resources/resources.xml");
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            XmlArchiveHandler groupHandler = new XmlArchiveHandler(criteria);
            saxParser.parse(resources, groupHandler);
            students = groupHandler.getStudents();

        } catch (Exception e) {
            throw new DAOException(e);
        }
        return students;
    }

    @Override
    public void delete(Criteria criteria) throws DAOException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("src/task/main/resources/resources.xml"));

            Element root = document.getDocumentElement();
            NodeList nodeStudents = document.getElementsByTagName("Student");
            List<Element> resultStudents = new ArrayList<>();
            for(int i = 0; i < nodeStudents.getLength(); i++) {
                if(elementEquals(criteria, (Element) nodeStudents.item(i)) || (criteria.get("all") != null)){
                    resultStudents.add((Element) nodeStudents.item(i));
                }
            }
            for (Element studentElement:
                    resultStudents) {
                studentElement.getParentNode().removeChild(studentElement);
            }
            saveResult(document);
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void add(Student student) throws DAOException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("src/task/main/resources/resources.xml"));

            Element root = document.getDocumentElement();
            Element newStudent = document.createElement("Student");
            newStudent.setAttribute("surname", student.getSurname());
            newStudent.setAttribute("name", student.getName());
            newStudent.setAttribute("id", Integer.toString(student.getStudentId()));
            newStudent.setAttribute("group", Integer.toString(student.getGroupNumber()));

            root.appendChild(newStudent);
            saveResult(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeStudentInfo(Criteria criteria, Criteria attributeToChange) throws DAOException {
        try {
            Set<String> searchesAttributes = attributeToChange.getCriteria();

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("src/task/main/resources/resources.xml"));

            NodeList nodeStudents = document.getElementsByTagName("Student");
            List<Element> resultStudents = new ArrayList<>();
            for(int i = 0; i < nodeStudents.getLength(); i++) {
                if(elementEquals(criteria, (Element) nodeStudents.item(i)) || (criteria.get("all") != null)){
                    resultStudents.add((Element) nodeStudents.item(i));
                }
            }
            for (Element studentElement:
                 resultStudents) {
                for (String attribute:
                        searchesAttributes) {
                    studentElement.setAttribute(attribute, attributeToChange.get(attribute).toString());
                }
            }
            saveResult(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DAOException();
        }

    }
    private boolean elementEquals(Criteria criteria, Element node) {
        Set<String> criteriaSearched = criteria.getCriteria();
        boolean isValid = true;
        for(String currentCriteria:
            criteriaSearched)
        {
            String attr = node.getAttribute(currentCriteria);
            isValid &= attr.equalsIgnoreCase(criteria.get(currentCriteria).toString());
        }
        return isValid;
    }
    private void saveResult(Document document) throws DAOException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("src/task/main/resources/resources.xml"));
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            throw new DAOException();
        }
    }
    private class XmlArchiveHandler extends DefaultHandler {
        private Criteria criteria;
        private List<Student> students;
        public XmlArchiveHandler(Criteria criteria)
        {
            this.criteria = criteria;
            students = new ArrayList<>();
        }
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
           if(qName.equalsIgnoreCase(criteria.getGroupSearchName())) {
               try {
                   Set<String> specifiedValues = criteria.getCriteria();
                   boolean isValid = true;
                   if(criteria.get("all") == null) {
                       for (String searchCriteria:
                               specifiedValues) {

                           isValid &= (criteria.get(searchCriteria).equals(attributes.getValue(searchCriteria)));
                       }
                   }
                   if(isValid)
                   {
                       int id = Integer.parseInt(
                               attributes.getValue(CriteriaTranslator.criteriaToString(SearchCriteria.Student.STUDENT_ID)));
                       int groupNumber = Integer.parseInt(
                               attributes.getValue(CriteriaTranslator.criteriaToString(SearchCriteria.Student.GROUP_NUMBER)));
                       String surname = attributes.getValue(CriteriaTranslator.criteriaToString(SearchCriteria.Student.SURNAME));
                       String name = attributes.getValue(CriteriaTranslator.criteriaToString(SearchCriteria.Student.NAME));

                       Student student = new Student(name, surname, groupNumber, id);
                       students.add(student);
                   }
               }
               catch (NumberFormatException | NullPointerException e) {
                   throw new SAXException(e);
               }
           }
        }
        public List<Student> getStudents()
        {
            return students;
        }
    }
}
