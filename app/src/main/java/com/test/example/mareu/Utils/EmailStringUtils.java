package com.test.example.mareu.Utils;

import java.util.Arrays;

public class EmailStringUtils {

    public static String getEmailsSplit(int partToReturn, String emails) {
        if (emails.contains(",")) {
            StringBuilder sb = new StringBuilder();
            String[] emailArray = emails.split(",");
            if (partToReturn == 1) {
                emailArray = Arrays.copyOfRange(emailArray, 0, emailArray.length / 2);
            } else if (partToReturn == 2) {
                emailArray = Arrays.copyOfRange(emailArray, emailArray.length / 2, emailArray.length);
            }
            for (String s : emailArray) sb.append(s).append("\n");
            sb.substring(0, sb.length() - 2);
            return formatEmails(sb.toString());
        }
        else return emails;
    }

    public static String sortEmailAlphabetically(String emails) {
        if (emails.contains(",")) {
            String[] emailArray = emails
                    .substring(0, emails.length() - 2)
                    .split(",");
            Arrays.sort(emailArray);

            emails = Arrays.toString(emailArray);
            emails = emails.replace("[", "")
                    .replace("]", "");
        }
        return emails;
    }

    public static String formatEmails(String emails) {
        return emails.replace(",", "\n").replace(" ", "");
    }
}
