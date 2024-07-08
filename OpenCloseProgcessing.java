package packs;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class OpenCloseProgcessing {
    static boolean installApp()throws IOException{
        System.out.println("Program is on install check procces, Please wait...");
        File mainProjectFile = new File("C://OOP_Project");
        File userFile = new File("C://OOP_Project//Users");
        File mainCardsFile = new File("C://OOP_Project//Cards");

        try{
            if(mainProjectFile.createNewFile())
            {
                System.out.println("Program main complated!");
                if(userFile.createNewFile()){
                    if(mainCardsFile.createNewFile()) {
                        System.out.println("Program install complated! Plese wait");
                        try {
                            Thread.sleep(1000L);
                            System.out.print(".");
                            Thread.sleep(1000L);
                            System.out.print(".");
                            Thread.sleep(1000L);
                            System.out.print(".");
                        } catch (InterruptedException ie) {

                        }
                        Perform.clearScreen();
                        return true;
                    }
                }
            }
            else
            {
                System.out.println("Program was installed! Plese wait");
                try {
                    Thread.sleep(1000L);
                    System.out.print(".");
                    Thread.sleep(1000L);
                    System.out.print(".");
                    Thread.sleep(1000L);
                    System.out.print(".");
                } catch (InterruptedException ie) {
                    System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
                }
                Perform.clearScreen();

                return true;
            }
        }catch(IOException e)
        {
            OpenCloseProgcessing.programWasEndInTen("Instal was not complated! Please try it later!");
        }

        return false;
    }

    public static void programWasEndInTen(String message) {
        int sec = 10;
        System.out.print(message+"\nProgram closed in ");
        while (sec >= 0){
            System.out.printf("%02d seconds..." , sec);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {

            }
            sec--;
            for(int i=0; i<13;i++)
                System.out.print("\b");
        }
        Perform.setRun(false);

    }

    static boolean processing(Perform perform){
        try{
            File userFile = new File ("C://OOP_Project//Users");
            File beginnerCardsFile = new File ("C://OOP_Project//Cards//BeginnerCards.txt");
            File specialCardsFile = new File ("C://OOP_Project//Cards//SpecialCards.txt");
            File addedCardsFile = new File ("C://OOP_Project//Cards//AddedCards.txt");
            int userNum = userFile.listFiles().length;

            //afzudan-e user-haye zakhire shode be hashmap
            if(userNum>0)
            {
                File[] usernames = userFile.listFiles();
                File user;
                for(int i=0; i<userNum; i++)
                {
                    perform.getUsersHashMap().put(usernames[i].getName(), new User(usernames[i]));

                }
            }

            //afzudan-e pack shoru konande
            Card.ArrayListCard(beginnerCardsFile,perform.getBeginnerCards());

            //afzudane cart haye special va added
            Card.ArrayListCard(specialCardsFile,perform.getSpecialCards());
            Card.ArrayListCard(addedCardsFile,perform.getAddedCards());

        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Initializing was failed!");}


        return false;
    }
}
