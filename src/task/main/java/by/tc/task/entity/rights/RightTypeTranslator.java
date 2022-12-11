package by.tc.task.entity.rights;

public final class RightTypeTranslator {
    public static RightType stringToRightType(String rightType) {
        RightType res = null;
        switch (rightType.toLowerCase()) {
            case "none" -> res = RightType.NONE;
            case "read" -> res = RightType.READ;
            case "modificate" -> res = RightType.MODIFICATE;
            case "delete" -> res = RightType.DELETE;
        }
        return res;
    }
    public static String rightTypeToString(RightType rightType) {
        String res = null;
        switch (rightType) {
            case NONE -> res="none";
            case READ -> res = "read";
            case MODIFICATE -> res = "modificate";
            case DELETE -> res = "delete";
        }

        return res;
    }
}
