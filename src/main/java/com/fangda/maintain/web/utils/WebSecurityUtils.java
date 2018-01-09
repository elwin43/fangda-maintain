package com.fangda.maintain.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * DESCRIPTION : Xss 工具类
 */
public class WebSecurityUtils {

    public static boolean containsXSSStrip(String value) {
        if (!StringUtils.isBlank(value)) {

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            //value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of e­xpression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
           // value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            //value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            //value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            //value = scriptPattern.matcher(value).replaceAll("");

            // Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            // value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... e­xpressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            // value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            matcher = scriptPattern.matcher(value);
            if (matcher.matches()) {
                return true;
            }
            // value = scriptPattern.matcher(value).replaceAll("");
        }
        return false;
    }
}
