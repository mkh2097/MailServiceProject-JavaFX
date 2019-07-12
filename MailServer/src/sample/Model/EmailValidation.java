package sample.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    public static  boolean emailValidator(String string)
    {
        String regex = "^[A-Za-z0-9+_.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
