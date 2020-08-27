package com.me.noban.DengueSolution.utilities;

public class StringUtils {

    public static boolean isNullOrEmpty(String... fields) {

        for (String field : fields) {
            if (field == null || field.length() == 0) {
                return true;
            }
        }

        return false;
    }
}
