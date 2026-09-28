package util;

public final class TextUtils {

    private TextUtils() {
    }

    public static String onlyDigits(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    public static boolean isPersonName(String value) {
        return value != null && value.matches("[\\p{L} .'-]+");
    }
}
