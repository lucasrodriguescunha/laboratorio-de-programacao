package ui;

import util.TextUtils;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.function.UnaryOperator;

public final class InputFilters {

    private InputFilters() {
    }

    public static void limit(JTextField field, int max) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                replace(fb, offset, 0, text, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)
                    throws BadLocationException {
                if (text == null) {
                    text = "";
                }
                int room = max - (fb.getDocument().getLength() - length);
                if (room <= 0) {
                    return;
                }
                super.replace(fb, offset, length, text.length() > room ? text.substring(0, room) : text, attr);
            }
        });
    }

    public static void digitMask(JTextField field, int maxDigits, UnaryOperator<String> formatter) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                replace(fb, offset, 0, text, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)
                    throws BadLocationException {
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                String result = current.substring(0, offset) + (text == null ? "" : text)
                        + current.substring(offset + length);
                apply(fb, result);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                String removed = current.substring(offset, offset + length);
                if (TextUtils.onlyDigits(removed).isEmpty()) {
                    while (offset > 0 && !Character.isDigit(current.charAt(offset - 1))) {
                        offset--;
                        length++;
                    }
                    if (offset > 0) {
                        offset--;
                        length++;
                    }
                }
                apply(fb, current.substring(0, offset) + current.substring(offset + length));
            }

            private void apply(FilterBypass fb, String raw) throws BadLocationException {
                String digits = TextUtils.onlyDigits(raw);
                if (digits.length() > maxDigits) {
                    digits = digits.substring(0, maxDigits);
                }
                super.replace(fb, 0, fb.getDocument().getLength(), formatter.apply(digits), null);
            }
        });
    }

    public static void digitsOnly(JTextField field, int maxDigits) {
        digitMask(field, maxDigits, UnaryOperator.identity());
    }
}
