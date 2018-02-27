package bi.bigroup.life.utils;

import java.util.regex.Pattern;

public class EmailUtils {
    public static boolean isEmailValid(String email) {
        final Pattern rfc2822 = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if (!rfc2822.matcher(email.toLowerCase()).matches())
            return false;
        return true;
    }
}