package by.tc.task.main.requests;

import java.util.HashMap;

public final class Requests {
    public static enum Request
    { LOGIN, READ, MODIFICATE, DELETE, ADD, EXIT};
    private static HashMap<String, Request> stringRequestTranslator  = new HashMap<>()
    { {
        put("login", Request.LOGIN);
        put("read", Request.READ);
        put("modificate", Request.MODIFICATE);
        put("delete", Request.DELETE);
        put("add", Request.ADD);
        put("exit", Request.EXIT);
    } };

    private static HashMap<Request, String> requestStringTranslator = new HashMap<>()
    { {
        put(Request.LOGIN, "login");
        put(Request.READ, "read");
        put(Request.MODIFICATE, "modificate");
        put(Request.DELETE, "delete");
        put(Request.ADD, "add");
        put(Request.EXIT, "exit");
    } };

    public static Requests.Request getRequest(String requestString) {
        if(stringRequestTranslator.containsKey(requestString.toLowerCase()))
            return stringRequestTranslator.get(requestString);
        else
            return null;
    }

}
