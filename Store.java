package packs;

import packs.enums.Commands;

import java.util.regex.Matcher;

public class Store {
    static boolean kharideNashodeMundeSpecial=true;
    static boolean kharideNashodeMundeAdded=true;
    static void run(Perform perform, User user)
    {
        Perform.clearScreen();
        System.out.println("Please choose one:");
        System.out.println("-upgrade my cards\n-buy special cards\n-buy added cards");
        boolean running=true;
        String inputLine;
        Matcher matcher;
        while(running) {
            inputLine = Perform.getInputLine();
            if (inputLine.matches(Commands.getRegex(Commands.END))) {
                Perform.setRun(false);
                break;
            } else if (inputLine.matches(Commands.getRegex(Commands.BACK_TO_MAIN_MENU))) {
                break;
            }else if (inputLine.matches(Commands.getRegex(Commands.UPGRADE_MY_CARDS))) {
                Store.upgradeMyCards(perform, user);
                Store.run(perform,user);
                break;
            }else if (inputLine.matches(Commands.getRegex(Commands.BUY_SPECIAL_CARDS))) {
                Store.buySpecialCard(perform, user);
                Store.run(perform,user);
                break;
            }else if (inputLine.matches(Commands.getRegex(Commands.BUY_ADDED_CARDS))) {
                Store.buyAddedCard(perform, user);
                Store.run(perform,user);
                break;
            }else {
                Perform.invalidCommand();
            }
        }

    }

    static void upgradeMyCards(Perform perform, User user){
        try{
            Perform.clearScreen();
            System.out.println("Please select card by type card name to upgrade it!");
            for(int i=0; i<user.getCards().size(); i++)
            {
                //print name/dur/att/dam/type/price/upgrade lvl/
                System.out.print(user.getCards().get(i).getCardString()+ " ");
                //if lvl and coin is ok print (ok to up/lock/coin not enough)
                if(user.getCards().get(i).upgrade_level>user.getLevel())
                    System.out.println(" | Lock, level not enough!");
                else if(user.getCards().get(i).upgrade_cost>user.getCoin())
                    System.out.println(" | Lock, coins not enough!");
                else
                    System.out.println(" | OK, you can upgrade it!");
            }
            boolean running=true;
            String inputLine;
            Matcher matcher;
            while(running)
            {
                inputLine=Perform.getInputLine();
                if (inputLine.matches(Commands.getRegex(Commands.END))) {
                    Perform.setRun(false);
                    break;
                }else if (inputLine.matches(Commands.getRegex(Commands.BACK_TO_MAIN_MENU))) {
                    break;
                }else {
                    boolean isTrue=false;
                    int andis=0;
                    for(int i=0; i<user.getCards().size(); i++)
                    {
                        if(user.getCards().get(i).Name.equals(inputLine)) {
                            isTrue = true;
                            andis = i;
                            break;
                        }
                    }
                    if(isTrue)
                    {
                        if(user.getLevel()<user.getCards().get(andis).upgrade_level){
                            System.out.println("Your level not enough!");
                            Perform.wait(3);
                            Perform.clear(23+inputLine.length()+1);
                        }else if(user.getCoin()<user.getCards().get(andis).upgrade_cost){
                            System.out.println("Your Coin not enough!");
                            Perform.wait(3);
                            Perform.clear(23+inputLine.length()+1);
                        }else{
                            //upgrade card

                            user.getCards().get(andis).upgrade_level*=1.4;
                            user.getCards().get(andis).upgrade_cost*=1.25;
                            if(user.getCards().get(andis).defence_attack<=90)
                                user.getCards().get(andis).defence_attack*=1.1;
                            if(user.getCards().get(andis).playerdamage<=90)
                                user.getCards().get(andis).playerdamage*=1.1;

                            //decrease coin
                            user.setCoin(user.getCoin()-user.getCards().get(andis).upgrade_cost);

                            System.out.println("Upgrade sucessfully!");
                            Perform.wait(3);
                            Perform.clear(21+inputLine.length()+1);
                        }
                    }
                }
            }
        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Upgrade card failed!");}
    }

    static void buySpecialCard(Perform perform, User user){
        try{
            Perform.clearScreen();
            System.out.println("Please select card by type card name to buy it!");
            kharideNashodeMundeSpecial=false;
            for(int i=0; i<perform.getSpecialCards().size(); i++)
            {
                boolean kharideShode=false;
                for(int j=0; j<user.getCards().size(); j++)
                {
                    if(perform.getSpecialCards().get(i)==user.getCards().get(j)){
                        kharideShode=true;
                    }else {
                        kharideNashodeMundeSpecial=true;
                    }
                }
                if(!kharideShode)
                {
                    //print name/dur/att/dam/type/price/upgrade lvl/
                    System.out.print(perform.getSpecialCards().get(i).getCardString()+ " ");
                    //if lvl and coin is ok print (ok to up/lock/coin not enough)
                    if(perform.getSpecialCards().get(i).upgrade_level>user.getLevel())
                        System.out.println(" | Lock, level not enough!");
                    else if(perform.getSpecialCards().get(i).upgrade_cost>user.getCoin())
                        System.out.println(" | Lock, coins not enough!");
                    else
                        System.out.println(" | unLock, you can buy it!");
                }
            }
            if(!kharideNashodeMundeSpecial)
            {
                Perform.clear(48);
                System.out.println("You have bought all the cards!\nReturning to the previous menu...");
                Perform.wait(3);
                Perform.clear(65);


            }
            else{
                boolean running=true;
                String inputLine;
                Matcher matcher;
                while(running)
                {
                    inputLine=Perform.getInputLine();
                    if (inputLine.matches(Commands.getRegex(Commands.END))) {
                        Perform.setRun(false);
                        break;
                    }else if (inputLine.matches(Commands.getRegex(Commands.BACK_TO_MAIN_MENU))) {
                        break;
                    }else {
                        boolean isTrue=false;
                        int andis=0;
                        for(int i=0; i<perform.getSpecialCards().size(); i++)
                        {
                            if(perform.getSpecialCards().get(i).Name.equals(inputLine)) {
                                isTrue = true;
                                andis = i;
                                break;
                            }
                        }
                        if(isTrue)
                        {
                            if(user.getLevel()<perform.getSpecialCards().get(andis).upgrade_level){
                                System.out.println("Your level not enough!");
                                Perform.wait(3);
                                Perform.clear(23+inputLine.length()+1);
                            }else if(user.getCoin()<perform.getSpecialCards().get(andis).price){
                                System.out.println("Your Coin not enough!");
                                Perform.wait(3);
                                Perform.clear(23+inputLine.length()+1);
                            }else{
                                //decrease coin
                                user.setCoin(user.getCoin()-perform.getSpecialCards().get(andis).upgrade_cost);
                                //add card
                                user.getCards().add(perform.getSpecialCards().get(andis));
                                //save
                                user.storageCards();

                                System.out.println("Buy sucessfully!");
                                Perform.wait(3);
                                Perform.clear(17+inputLine.length()+1);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("buy Special-card failed!");}
    }

    static void buyAddedCard(Perform perform, User user){
        try{
            Perform.clearScreen();
            System.out.println("Please select card by type card name to buy it!");
            kharideNashodeMundeAdded=false;
            for(int i=0; i<perform.getAddedCards().size(); i++)
            {
                boolean kharideShode=false;
                for(int j=0; j<user.getCards().size(); j++)
                {
                    if(perform.getAddedCards().get(i)==user.getCards().get(j)){
                        kharideShode=true;
                    }else {
                        kharideNashodeMundeAdded=true;
                    }
                }
                if(!kharideShode)
                {
                    //print name/dur/att/dam/type/price/upgrade lvl/
                    System.out.print(perform.getAddedCards().get(i).getCardString()+ " ");
                    //if lvl and coin is ok print (ok to up/lock/coin not enough)
                    if(perform.getAddedCards().get(i).upgrade_level>user.getLevel())
                        System.out.println(" | Lock, level not enough!");
                    else if(perform.getAddedCards().get(i).upgrade_cost>user.getCoin())
                        System.out.println(" | Lock, coins not enough!");
                    else
                        System.out.println(" | unLock, you can buy it!");
                }
            }
            if(!kharideNashodeMundeAdded)
            {
                Perform.clear(48);
                System.out.println("You have bought all the cards!\nReturning to the previous menu...");
                Perform.wait(3);
                Perform.clear(65);


            }
            else{
                boolean running=true;
                String inputLine;
                Matcher matcher;
                while(running)
                {
                    inputLine=Perform.getInputLine();
                    if (inputLine.matches(Commands.getRegex(Commands.END))) {
                        Perform.setRun(false);
                        break;
                    }else if (inputLine.matches(Commands.getRegex(Commands.BACK_TO_MAIN_MENU))) {
                        break;
                    }else {
                        boolean isTrue=false;
                        int andis=0;
                        for(int i=0; i<perform.getAddedCards().size(); i++)
                        {
                            if(perform.getAddedCards().get(i).Name.equals(inputLine)) {
                                isTrue = true;
                                andis = i;
                                break;
                            }
                        }
                        if(isTrue)
                        {
                            if(user.getLevel()<perform.getAddedCards().get(andis).upgrade_level){
                                System.out.println("Your level not enough!");
                                Perform.wait(3);
                                Perform.clear(23+inputLine.length()+1);
                            }else if(user.getCoin()<perform.getAddedCards().get(andis).price){
                                System.out.println("Your Coin not enough!");
                                Perform.wait(3);
                                Perform.clear(23+inputLine.length()+1);
                            }else{
                                //increase coin
                                user.setCoin(user.getCoin()-perform.getAddedCards().get(andis).upgrade_cost);
                                //add card
                                user.getCards().add(perform.getAddedCards().get(andis));
                                System.out.println("Buy sucessfully!");
                                //save
                                user.storageCards();

                                Perform.wait(3);
                                Perform.clear(17+inputLine.length()+1);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Upgrade Added-card failed!");}
    }
}
