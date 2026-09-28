package util;

public final class PhoneUtils {

    private PhoneUtils() {
    }

    public static boolean isValid(String digits) {
        if (digits == null || !digits.matches("[1-9]{2}\\d{8,9}")) {
            return false;
        }
        char third = digits.charAt(2);
        if (digits.length() == 11) {
            return third == '9';
        }
        return third >= '2' && third <= '5';
    }

    public static String format(String digits) {
        int n = digits.length();
        if (n == 0) {
            return "";
        }
        if (n <= 2) {
            return "(" + digits;
        }
        String ddd = digits.substring(0, 2);
        String number = digits.substring(2);
        int split = (n == 11) ? 5 : 4;
        if (number.length() <= split) {
            return "(" + ddd + ") " + number;
        }
        return "(" + ddd + ") " + number.substring(0, split) + "-" + number.substring(split);
    }
}
