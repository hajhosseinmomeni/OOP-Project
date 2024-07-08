package packs;

import packs.enums.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

interface PerformAbstract{
    public HashMap<String, User> getUsersHashMap();
    public boolean getIsLogin();
    public String getLogedinUsername();
    public ArrayList<Card> getSpecialCards();
    public ArrayList<Card> getAddedCards();
    public ArrayList<Card> getBeginnerCards();

    public void setIsLogedinBoolean(boolean b);
    public void setLogedinUsername(String newUsername);
    public void run(Perform perform);
    //public void static setRun(boolean b)
    //public static void clearScreen()

}

public class Perform implements PerformAbstract{
    private HashMap<String,User> users;
    private boolean isLogin=false;
    private static boolean run= true;
    private String logedinUsername ;
    private ArrayList<Card> BeginnerCards= new ArrayList<>(),SpecialCards= new ArrayList<>(),AddedCards= new ArrayList<>();//added cards means cards added by admin
    public Perform() {
        users = new HashMap<String,User>();
        logedinUsername = new String();
        ArrayList<Card> SpecialCards= new ArrayList<>();
        ArrayList<Card> AddedCards= new ArrayList<>();
        ArrayList<Card> BeginnerCards= new ArrayList<>();
    }

    public HashMap<String, User> getUsersHashMap(){return this.users;}
    public boolean getIsLogin(){return this.isLogin;}
    public String getLogedinUsername(){return this.logedinUsername;}
    public ArrayList<Card> getSpecialCards(){return this.SpecialCards;}
    public ArrayList<Card> getAddedCards(){return this.AddedCards;}
    public ArrayList<Card> getBeginnerCards(){return this.BeginnerCards;}
    public void setIsLogedinBoolean(boolean b){this.isLogin=b;}
    public void setLogedinUsername(String newUsername){this.logedinUsername=newUsername;}


    public void run(Perform perform)
    {
        String inputLine;
        OpenCloseProgcessing.processing(perform);
        while (Perform.run) {

            //if and else
            Perform.clearScreen();
            while(!perform.isLogin)
                LoginOrSignup.chooseOne(perform);
            if(perform.isLogin)
            {
                Perform.clearScreen();
                User user= perform.getUsersHashMap().get(perform.getLogedinUsername());
                System.out.println("Welcome "+user.getUsername()+"!\n");
                System.out.println("-see profile");
                System.out.println("-start the game");
                System.out.println("-see your play history (see history)");
                System.out.println("-go to Store or see cards state (go to store)");
                System.out.println("-log out");
                System.out.println("-exit game (end)");


                inputLine = Perform.getInputLine();
                if (inputLine.matches(Commands.getRegex(Commands.END))) {
                    Perform.setRun(false);
                    break;
                } else if (inputLine.matches(Commands.getRegex(Commands.LOG_OUT))) {
                    perform.setIsLogedinBoolean(false);
                }else if (inputLine.matches(Commands.getRegex(Commands.SEE_PROFILE))) {
                    user.showProfile(perform);
                }else if (inputLine.matches(Commands.getRegex(Commands.PLAY_GAME))) {
                    //play tow
                }else if (inputLine.matches(Commands.getRegex(Commands.SEE_HISTORY))) {
                    PlayHistory.printlnListHistory(user.getPlayerHistory(),"time",false,0);
                }else if (inputLine.matches(Commands.getRegex(Commands.GO_TO_STORE))) {
                    Store.run(perform,user);
                }else {
                    Perform.invalidCommand();
                }
            }



        }
    }



    public static void setRun(boolean b){Perform.run=b;}
    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getInputLine()
    {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine().trim();
    }

    public static void invalidCommand(){
        System.out.println("Command was invalid!\nPlease enter again...");
        Perform.wait(3);
        for(int i=0 ;i<43; i++){System.out.print("\b");}
    }

    public static void wait(int sec){
        for(int i=0; i<sec; i++)
        {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {
                OpenCloseProgcessing.programWasEndInTen("Somethings in sleep went wrong!");
            }
        }
    }

    public static void clear(int charNum) {
        for(int i=0 ;i<charNum; i++){System.out.print("\b");}
    }

    public static boolean captcha(){
        String[] operators={"PLUS","MINUS","TIMES","+","-","*","+"};
        String[] nums={"ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE",
        "0","1","2","3","4","5","6","7","8","9","0"};


        Random rand=new Random(System.currentTimeMillis()*System.currentTimeMillis()+4*System.currentTimeMillis()+3);
        int capchaAns=3,x;String captchaOut;


        Perform.clearScreen();
        boolean running = true;
        String inputLine="0" ;
        Matcher matcher;

        while(Integer.valueOf(inputLine)!=capchaAns) {
            System.out.println("Pleese enter answer of captcha:");
            capchaAns=0;captchaOut="";
            int rand1=rand.nextInt(20);
            int rand2=rand.nextInt(6);
            int rand3=rand.nextInt(20);
            int rand4=rand.nextInt(6);
            int rand5=rand.nextInt(20);
            switch(rand2%3)
            {
                case 0:
                    switch(rand4%3)
                    {
                        case 0:
                            capchaAns=rand1%10+rand3%10+rand5%10;
                            break;
                        case 1:
                            capchaAns=rand1%10+rand3%10-rand5%10;
                            break;
                        case 2:
                            capchaAns=rand1%10+(rand3%10)*(rand5%10);
                            break;
                    }
                    break;
                case 1:
                    switch(rand4%3)
                    {
                        case 0:
                            capchaAns=rand1%10-rand3%10+rand5%10;
                            break;
                        case 1:
                            capchaAns=rand1%10-rand3%10-rand5%10;
                            break;
                        case 2:
                            capchaAns=rand1%10-(rand3%10)*(rand5%10);
                            break;
                    }
                    break;
                case 2:
                    switch(rand4%3)
                    {
                        case 0:
                            capchaAns=(rand1%10)*(rand3%10)+rand5%10;
                            break;
                        case 1:
                            capchaAns=(rand1%10)*(rand3%10)-rand5%10;
                            break;
                        case 2:
                            capchaAns=(rand1%10)*(rand3%10)*(rand5%10);
                            break;
                    }
                    break;
            }
            captchaOut=nums[rand1]+" "+operators[rand2]+" "+nums[rand3]+" "+operators[rand4]+" "+nums[rand5]+" = ";
            System.out.println(captchaOut);


            inputLine = Perform.getInputLine();
            if (Integer.valueOf(inputLine)!=capchaAns) {
                System.out.println("Captcha not correct!\nPlease try again.");
            }else{
                System.out.println("Captcha is correct!\nPlease wait.");
                Perform.wait(2);
                Perform.clearScreen();
                return true;
            }
            Perform.wait(3);
            Perform.clearScreen();
        }

        return false;
    }



}

