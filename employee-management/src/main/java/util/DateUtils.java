
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private DateUtils() {
    }

    public static String format(LocalDate date) {
        return date == null ? "" : date.format(FORMATTER);
    }

    public static LocalDate parse(String text) {
        try {
            return LocalDate.parse(text.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static String mask(String digits) {
        StringBuilder masked = new StringBuilder(digits);
        if (masked.length() > 4) {
            masked.insert(4, '/');
        }
        if (masked.length() > 2) {
            masked.insert(2, '/');
        }
        return masked.toString();
    }
}
