package by.tc.task.entity.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Criteria {
    private String groupSearchName;

    private Map<String, Object> criteria;

    public Criteria() {
        criteria = new HashMap<>();
    }

    public Criteria(String groupSearchName) {
        this.groupSearchName = groupSearchName;
        this.criteria = new HashMap<>();
    }

    public String getGroupSearchName() {
        return groupSearchName;
    }
    public void add(String searchCriteria, Object value) {
        criteria.put(searchCriteria.toLowerCase(), value);
    }

    public Object get(String criteriaName) {
        if(!criteria.containsKey(criteriaName.toLowerCase()))
            return null;
        return criteria.get(criteriaName);
    }
    public Set<String> getCriteria() {
        return criteria.keySet();
    }
}
