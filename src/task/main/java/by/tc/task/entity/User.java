package by.tc.task.entity;

import by.tc.task.entity.rights.RightType;
import by.tc.task.entity.rights.RightTypeTranslator;

public class User {
    private String login;
    private String hashedPassword;
    private RightType rights;
    public User(String login, String hashedPassword, RightType rights) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.rights = rights;
    }
    public String getLogin() {
        return login;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public RightType getRights() {
        return rights;
    }
    @Override
    public String toString() {
        return "User { " +
                "login = " + this.getLogin() +
                ", hashed password = " + this.getHashedPassword() +
                ", rights = " + RightTypeTranslator.rightTypeToString(this.getRights()) +
                " }";
    }
}
