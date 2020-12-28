package com.ling.other.common.utils;

import com.ling.other.common.exception.RrException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static String getMessage(Exception e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        e.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception ex) {
            throw new RrException(ex);
        }
        return ret;
    }
}