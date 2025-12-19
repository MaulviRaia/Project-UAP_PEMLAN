package org.example.kasirtoko.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatterUtil {
    public static String rupiah(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("id","ID")).format(v);
    }
}
