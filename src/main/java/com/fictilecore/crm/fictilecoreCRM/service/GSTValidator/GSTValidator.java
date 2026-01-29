package com.fictilecore.crm.fictilecoreCRM.service.GSTValidator;


import java.util.regex.Pattern;

public class GSTValidator {

    private static final String GST_REGEX =
            "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";

    private static final Pattern GST_PATTERN = Pattern.compile(GST_REGEX);

    public static boolean isValid(String gst) {
        if (gst == null) return false;
        return GST_PATTERN.matcher(gst.toUpperCase().trim()).matches();
    }
}
