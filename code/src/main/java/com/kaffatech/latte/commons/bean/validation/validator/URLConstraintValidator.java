package com.kaffatech.latte.commons.bean.validation.validator;

import com.kaffatech.latte.commons.bean.validation.annotation.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA
 * User: Alan
 * Date: 17/7/24
 */
public class URLConstraintValidator implements ConstraintValidator<URL, String> {

    private static final String URL_REGEX = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

    @Override
    public void initialize(URL urlString) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (null == s) {
            return true;
        }
        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static void main(String[] args) {
        URLConstraintValidator x = new URLConstraintValidator();
        System.out.println(x.isValid("http://www.baidu.com/notify/", (ConstraintValidatorContext) null));
    }
}
