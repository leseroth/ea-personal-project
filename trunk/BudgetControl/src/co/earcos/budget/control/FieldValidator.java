package co.earcos.budget.control;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FieldValidator extends PlainDocument {

    // Validation
    public static final int VALIDATE_TEXT = 1;
    public static final int VALIDATE_CURRENCY = 2;
    // Attributes
    private int max;
    private int validation;

    public FieldValidator(int max, int validation) {
        this.max = max;
        this.validation = validation;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str != null && str.length() != 0) {
            StringBuffer sb = new StringBuffer();
            char[] sarray = str.toCharArray();

            switch (validation) {
                case VALIDATE_CURRENCY:
                    for (int i = 0; i < sarray.length; i++) {
                        if (Character.isDigit(sarray[i]) || sarray[i] == '.' || sarray[i] == '-') {
                            sb.append(sarray[i]);
                        }
                    }
                    str = sb.toString();
                    break;
            }

            if (max != 0 && getLength() + str.length() > max) {
                str = str.substring(0, max - getLength());
            }
            super.insertString(offs, str, a);
        }
    }
}
