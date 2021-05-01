package com.bitlegion.server.accounts;

import java.util.ArrayList;

//Program to validate Password
public class PasswordValidator {
  public static ArrayList<String> errorStrings = new ArrayList<String>();

  public static ArrayList<String> Validator(String password) {
    // For password length
    if (password.length() < 8) {
      errorStrings.add("The password should be of at least 8 characters");
    }

    // For space in password
    if (password.contains(" ")) {
      errorStrings.add("Password should not have space in it");
    }

    // for integer in password
    if (true) {
      boolean flag = false;

      for (int n = 0; n <= 9; n++) {
        // int to String
        String str = String.valueOf(n);

        if (password.contains(str)) {
          flag = true;
          break;
        }
      }

      // Not even 1 integer
      if (flag == false) {
        errorStrings.add("Password should have at least one integer (0-9)");
      }
    }

    // for Higher Case letters
    if (true) {
      boolean flag = false;

      for (int n = 65; n <= 90; n++) {
        // ASCII
        char c = (char) n;

        // Character to String
        String str = String.valueOf(c);

        if (password.contains(str)) {
          flag = true;
          break;
        }
      }

      if (flag == false) {
        errorStrings.add("Password should have at least one higher case letter (A-Z)");
      }
    }

    // for Lower Case letters
    if (true) {
      boolean flag = false;

      for (int n = 97; n <= 122; n++) {

        char ch = (char) n;

        String str = String.valueOf(ch);

        if (password.contains(str)) {
          flag = true;
          break;
        }
      }

      if (flag == false) {
        errorStrings.add("Password should have at least one lower case letter (a-z)");
      }
    }

    // For special Characters
    if (!(password.contains("@") || password.contains("#") || password.contains("!") || password.contains("~")
        || password.contains(":") || password.contains(".") || password.contains("$") || password.contains("%")
        || password.contains("^") || password.contains("&") || password.contains("*") || password.contains("(")
        || password.contains(")") || password.contains("-") || password.contains("+") || password.contains("/")
        || password.contains(",") || password.contains("<") || password.contains(">") || password.contains("?")
        || password.contains("|"))) {
      errorStrings.add("Password should have at least one special character");
    }
    return errorStrings;
  }
}
