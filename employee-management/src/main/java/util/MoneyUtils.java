package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class MoneyUtils {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private MoneyUtils() {
    }

    public static String format(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(PT_BR)).format(value);
    }

    public static String formatCurrency(BigDecimal value) {
        return value == null ? "" : "R$ " + format(value);
    }

    public static BigDecimal fromCents(String digits) {
        String cents = TextUtils.onlyDigits(digits);
        return cents.isEmpty() ? null : new BigDecimal(cents).movePointLeft(2);
    }

    public static String mask(String digits) {
        return format(fromCents(digits));
    }
}
