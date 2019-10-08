package com.nurul.simpakat.common.util;

public class TextUtils {

    public static final boolean equalIgnoreCase(String var1, String var2) {
        boolean result = false;

        if (!android.text.TextUtils.isEmpty(var1) &&
            !android.text.TextUtils.isEmpty(var1)) {
            result = var1.equalsIgnoreCase(var2);
        }

        return result;
    }
}
