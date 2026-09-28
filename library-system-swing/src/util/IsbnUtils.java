package util;

public final class IsbnUtils {

    private IsbnUtils() {
    }

    public static boolean hasThirteenDigits(String digits) {
        return digits != null && digits.matches("\\d{13}");
    }

    public static boolean isBrazilian(String digits) {
        return digits.startsWith("97885") || digits.startsWith("97865");
    }

    public static boolean hasValidCheckDigit(String digits) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int d = digits.charAt(i) - '0';
            sum += (i % 2 == 0) ? d : d * 3;
        }
        int check = (10 - sum % 10) % 10;
        return check == digits.charAt(12) - '0';
    }

    public static String format(String digits) {
        int[] sizes = {3, 2, 7, 1};
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        for (int size : sizes) {
            if (pos >= digits.length()) {
                break;
            }
            if (pos > 0) {
                sb.append('-');
            }
            int end = Math.min(pos + size, digits.length());
            sb.append(digits, pos, end);
            pos = end;
        }
        return sb.toString();
    }
}
