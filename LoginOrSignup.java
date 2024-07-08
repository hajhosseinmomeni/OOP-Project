package packs;


import packs.enums.Commands;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

abstract class LoginOrSignupData{

}
interface LoginOrSignupMethod{

}
public class LoginOrSignup extends LoginOrSignupData implements LoginOrSignupMethod{


    public static boolean chooseOne(Perform perform){
        Perform.clearScreen();
        boolean running = true;
        String inputLine ;
        Matcher matcher;

        while(!perform.getIsLogin())
        {
            System.out.println("Pleese login or sign-up!");
            inputLine = Perform.getInputLine();
            if (inputLine.matches(Commands.getRegex(Commands.END)))
            {
                Perform.setRun(false);
                return false;
            }
            else if (inputLine.matches(Commands.getRegex(Commands.LOGIN_USER))) {
                matcher = Commands.getMatcher(inputLine, Commands.LOGIN_USER);
                LoginOrSignup.login(perform, matcher);
            }else if (inputLine.matches(Commands.getRegex(Commands.SIGNUP_USER))) {
                matcher = Commands.getMatcher(inputLine, Commands.SIGNUP_USER);
                LoginOrSignup.signup(perform, matcher);
            }else if (inputLine.matches(Commands.getRegex(Commands.SIGNUP_USER_WITH_RANDOM_PASSWORD))) {
                matcher = Commands.getMatcher(inputLine, Commands.SIGNUP_USER_WITH_RANDOM_PASSWORD);
                LoginOrSignup.signupWithRandomPassword(perform, matcher);
            }else{
                Perform.invalidCommand();
            }
        }
        return true;
    }
    public static void pleaseTryAgainIn(int incorrectPasswordEntriesNum){
        String message = "Password and Username don't match!\nPlease try again in ";
        int sec = 5*incorrectPasswordEntriesNum;
        System.out.print(message);
        while (sec >= 0){
            System.out.printf("%03d seconds..." , sec);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {
                System.out.println("Somwthings in sleep went wrong!");
            }
            sec--;
            for(int i=0; i<14;i++)
                System.out.print("\b");
        }
        Perform.clearScreen();
    }

    public static void login(Perform perform, Matcher userMatcher){
        perform.setIsLogedinBoolean(false);
        String username = userMatcher.group("username").trim();
        String password = userMatcher.group("password").trim();
        if(!perform.getUsersHashMap().containsKey(username))
            System.out.println("Username doesn't exist!");
        else if(!password.equals(perform.getUsersHashMap().get(username).getPassword()))
            System.out.println("Password and Username don't match!");
        else{
            while(!Perform.captcha()) {}

            perform.setLogedinUsername(username);
            perform.setIsLogedinBoolean(true);
            System.out.println("Hi "+ username + "!\nYou are logged in sucessfuly!");

        }

        Perform.wait(3);
        Perform.clearScreen();

        //if (!userMatcher.matches())
        //    OpenCloseProgcessing.programWasEndInTen("Something in PlayerHistory of User: " + username + " is wrong");
    }


    public static void signup (Perform perform, Matcher userMatcher){
        try{
            perform.setIsLogedinBoolean(false);
            //true means sign-up complated
            String username = userMatcher.group("username").trim();
            String password = userMatcher.group("password").trim();
            String passwordConfirmation = userMatcher.group("passwordConfirmation").trim();
            String email = userMatcher.group("email").trim();
            String nickname = userMatcher.group("nickname").trim();


            if(perform.getUsersHashMap().containsKey(username))
                System.out.println("Username was taken!");
            else if(!username.matches(Commands.getRegex(Commands.USERNAME_FORMAT)))
                System.out.println("Invalid username!\nPlease use a username that has the correct format...");
            else if(!password.equals(passwordConfirmation)) {
                System.out.println("Password Confirmation not matches!\nPlease enter details again:");
            }else if(password.length()<8) {
                System.out.println("Password must have at least 8 characters!\nPlease enter details again:");
            }else if(!password.matches("\\S*[a-z]\\S*") ||!password.matches("\\S*[a-z]\\S*")
                    || !password.matches("\\S*[!@#$%^&*()_+]\\S*")) {
                System.out.println("Password must have at least 1 little, big and !@#$%^&*()_+ characters!");
                System.out.println("Please enter it again:");
            }else if(!email.matches("([\\w]+)[@]([\\w]+)(\\.com)"))
                System.out.println("Email address was invalid!\nPlease enter details again:");
            else if(!nickname.matches(Commands.getRegex(Commands.USERNAME_FORMAT)))
                System.out.println("Invalid nickname!\nPlease use a nickname that has the correct format...");
            else{
                Perform.captcha();
                User user = new User(username,password,nickname,email,perform);
                perform.setLogedinUsername(username);
                LoginOrSignup.ChooseSecurityQuestion(user);
                //enter begginer card pack to new user

                //add new user to users of perform
                perform.getUsersHashMap().put(user.getUsername(), user);
                perform.setIsLogedinBoolean(true);
                System.out.println("Sign-up completed sucessfully!");
            }
        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Sign-up processing failed!");}

        Perform.wait(4);
        Perform.clearScreen();
    }

    private static void ChooseSecurityQuestion(User user) {
        try {
            Perform.clearScreen();
            String[] questions = {"What is the name of my first teacher?",
                    "Where is my birth certificate issued?", "Where is my favorite place?"};
            System.out.println("Choose one question for security question:");
            System.out.println("1- What is the name of my first teacher?");
            System.out.println("2- Where is my birth certificate issued?");
            System.out.println("3- Where is my favorite place?");
            boolean running = true;
            String inputLine;
            Matcher matcher;
            while (running) {
                inputLine = Perform.getInputLine();
                while (!(Integer.valueOf(inputLine) < 4 && Integer.valueOf(inputLine) > 0)) {
                    System.out.println("Plese enter number of your selected question!");
                    Perform.wait(3);
                    Perform.clear(36);
                    inputLine = Perform.getInputLine();
                }
                user.setSecurityQuestion(questions[Integer.valueOf(inputLine)]);
                System.out.println("Plese enter your answer:");
                user.setSecurityAnswer(Perform.getInputLine());


            }
        } catch (Exception e) {
            OpenCloseProgcessing.programWasEndInTen("Security q/a processing failed!");
        }
    }


    public static void signupWithRandomPassword (Perform perform, Matcher userMatcher){
        try{
            perform.setIsLogedinBoolean(false);
            //true means sign-up complated
            String username = userMatcher.group("username").trim();
            String email = userMatcher.group("email").trim();
            String nickname = userMatcher.group("nickname").trim();

            //ساخت رمز رندوم
            Random rand=new Random(1235);
            int r= rand.nextInt(8)+8;
            String password=LoginOrSignup.generateRandomPassword(r);
            System.out.println("Your random password is: "+ password);
            System.out.println("Please confirm random password!");

            boolean running = true;
            String inputLine ;
            Matcher matcher;
            inputLine = Perform.getInputLine();
            while(!inputLine.equals(password)) {
                System.out.println("Confirmation not correct!\nPleese try again.");
                Perform.wait(3);
                Perform.clear(44);
                inputLine = Perform.getInputLine();
            }
            password=inputLine;
            System.out.println("Password set sucessfully!");
            Perform.wait(3);
            Perform.clear(26);

            if(perform.getUsersHashMap().containsKey(username))
                System.out.println("Username was taken!");
            else if(!username.matches(Commands.getRegex(Commands.USERNAME_FORMAT)))
                System.out.println("Invalid username!\nPlease use a username that has the correct format...");
            else if(!email.matches("([\\w]+)[@]([\\w]+)(\\.com)"))
                System.out.println("Email address was invalid!\nPlease enter details again:");
            else if(!nickname.matches(Commands.getRegex(Commands.USERNAME_FORMAT)))
                System.out.println("Invalid nickname!\nPlease use a nickname that has the correct format...");
            else{
                Perform.captcha();
                User user = new User(username,password,nickname,email,perform);
                perform.setLogedinUsername(username);
                LoginOrSignup.ChooseSecurityQuestion(user);
                //enter begginer card pack to new user
                //add new user to users of perform
                perform.getUsersHashMap().put(user.getUsername(), user);
                perform.setIsLogedinBoolean(true);
                System.out.println("Sign-up completed sucessfully!");
            }
        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Sign-up processing failed!");}

        Perform.wait(4);
        Perform.clearScreen();
    }

    public static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(validChars.length());
            password.append(validChars.charAt(randomIndex));
        }

        return password.toString();
    }
}
