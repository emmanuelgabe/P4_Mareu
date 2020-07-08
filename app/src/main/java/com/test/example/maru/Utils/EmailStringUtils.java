package com.test.example.maru.Utils;

import java.util.Arrays;

public abstract class EmailStringUtils {

    public static String getEmailsSplit(int partToReturn, String emails) {
        String[] emailArray = emails.split(",");
        String partEmails = "";
        for (int i = 0; i < emailArray.length; i++) {
            if (partToReturn == 1 && i <= emailArray.length / 2) {
                partEmails = partEmails + emailArray[i] + "\n";
            } else if (partToReturn == 2 && i > emailArray.length / 2) {
                partEmails = partEmails + emailArray[i] + "\n";
            }
        }
        partEmails = partEmails.replaceAll(" ", "");
        return partEmails;
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
