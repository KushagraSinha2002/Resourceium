import java.util.ArrayList;

//Program to validate Password

public class PasswordValidator
{
  public static ArrayList<Integer> errors = new ArrayList<Integer>();
  public static void Validator(String password)
  {
    // For password length
    if (password.length() < 8){
      errors.add(1);
    }

    // For space in password
    if (password.contains(" ")){
      errors.add(2);
    }

    // for integer in password
    if (true){
      boolean flag = false;

      for (int n = 0; n <= 9; n++){
        //int to String
        String str = String.valueOf(n);

        if (password.contains(str)){
          flag = true;
          break;
        }
      }

      // Not even 1 integer
      if (flag == false){
        errors.add(3);
      }
    }

    // for Higher Case letters
    if (true){
      boolean flag = false;

      for (int n = 65; n <= 90; n++){
        //ASCII
        char c = (char)n;

        //Character to String
        String str = String.valueOf(c);

        if (password.contains(str)){
          flag = true;
          break;
        }
      }

      if (flag == false){
        errors.add(4);
      }
    }

    // for Lower Case letters
    if (true){
      boolean flag = false;

      for (int n = 97; n <= 122; n++){

        char ch = (char) n;

        String str = String.valueOf(ch);

        if (password.contains(str)){
          flag = true;
          break;
        }
      }

      if (flag == false){
        errors.add(5);
      }
    }

    // For special Characters
    if (!(password.contains("@") || password.contains("#")
          || password.contains("!") || password.contains("~")
          || password.contains(":") || password.contains(".")
          || password.contains("$") || password.contains("%")
          || password.contains("^") || password.contains("&")
          || password.contains("*") || password.contains("(")
          || password.contains(")") || password.contains("-")
          || password.contains("+") || password.contains("/")
          || password.contains(",") || password.contains("<")
          || password.contains(">") || password.contains("?")
          || password.contains("|"))) {
            errors.add(6);
          }

    if (errors.size() > 0){

      for (int i: errors){

        //cases for respective errors
        switch(i){

          // For password length
          case 1:
            System.out.println("The password should be of atleast 8 Characters");
            break;

          //for space in password
          case 2:
            System.out.println ("Password should not have space in it");
            break;

          //for integer in password
          case 3:
            System.out.println("Password should have atleast one integer(0-9)");
            break;

          //for Higher case letters
          case 4:
            System.out.println("Password should have atleast one Higher case letter(A-Z)");
            break;

          //for lower case letter
          case 5:
            System.out.println("Password should have atleast one Lower case letter(a-z)");
            break;

          //for special Characters
          case 6:
            System.out.println("Password should have atleast one special character");
            break;
        }
      }
    }

  }

}
