package by.tc.task.entity.criteria;

public final class CriteriaTranslator {
    public static String criteriaToString(SearchCriteria.Student criteriaEnum)
    {
        String res = null;
        switch (criteriaEnum) {
            case ALL -> res = "all";
            case GROUP_NUMBER -> res = "group";
            case STUDENT_ID -> res = "id";
            case SURNAME -> res = "surname";
            case NAME -> res = "name";
        }
        return res;
    }
    public static SearchCriteria.Student criteriaToEnum(String stringCriteria)
    {
        SearchCriteria.Student res = null;
        switch (stringCriteria.toLowerCase()){
            case "all" -> res = SearchCriteria.Student.ALL;
            case "group" -> res = SearchCriteria.Student.GROUP_NUMBER;
            case "id" -> res = SearchCriteria.Student.STUDENT_ID;
            case "name" -> res = SearchCriteria.Student.NAME;
            case "surname" ->res = SearchCriteria.Student.SURNAME;
        }
        return res;
    }
}
