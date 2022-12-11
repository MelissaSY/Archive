package by.tc.task.service.validation;

import by.tc.task.entity.criteria.Criteria;

import java.util.Arrays;
import java.util.Set;

public class Validator {
    public static boolean criteriaValidator(Criteria criteria)
    {
        String[] validCriteria = {"all", "group", "id", "name", "surname"};
        Set<String> criteriaAll = criteria.getCriteria();
        boolean isValid = true;
        for (String criteriaSingle:
             criteriaAll) {
            isValid &= Arrays.asList(validCriteria).contains(criteriaSingle);
        }
        return isValid && criteria.getGroupSearchName().equals("Student");
    }
}
