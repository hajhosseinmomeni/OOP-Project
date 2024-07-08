package packs;
import packs.enums.Commands;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;

abstract class userData{
    final static int beginnerHP= 100, beginnerCoin = 100;
    protected String username,password,nickname,email;
    protected String securityQuestion,securityAnswer;
    protected ArrayList<Card> cards;
    protected ArrayList<PlayHistory> playerHistory;
    //yyyy.MM.dd hh:mm you (win/lose) in play with user2 by level user2Level
    protected double level;
    protected int HP,coin;

}
interface userMethod{
    public ArrayList<Card> getCards();
    public ArrayList<PlayHistory> getPlayerHistory();
    public Card getCard(int andis);
    public String getUsername();
    public String getNickname();
    public String getPassword();
    public String getEmail();
    public String getSecurityQuestion();
    public String getSecurityAnswer();
    public double getLevel();
    public int getHP();
    public int getCoin();


    public void setCard(int andis, Card card);
    public void addToPlayerHistory(PlayHistory newHistory);
    public void setUsername(String username);
    public void setNickname(String nickname);
    public void setPassword(String password);
    public void setEmail(String email);
    public void setSecurityQuestion(String question);
    public void setSecurityAnswer(String answer);
    public void setLevel(double level);
    public void setHP(int HP);
    public void setCoin(int coin);

    public void showProfile(Perform perform);
    public void storageDetails();
    public void storageInformation();
    public void storageCards();

}
public class User extends userData implements userMethod{
    public ArrayList<Card> getCards(){ return this.cards;}
    public ArrayList<PlayHistory> getPlayerHistory(){ return this.playerHistory;}
    public Card getCard(int andis){ return this.cards.get(andis);}
    public String getUsername(){ return this.username;}
    public String getNickname(){ return this.nickname;}
    public String getPassword(){return this.password;}
    public String getEmail(){ return this.email;}
    public String getSecurityQuestion(){ return this.securityQuestion;}
    public String getSecurityAnswer(){ return this.securityAnswer;}
    public double getLevel(){ return this.level;}
    public int getHP(){ return this.HP;}
    public int getCoin(){ return this.coin;}


    public void setCard(int andis,  Card card){this.cards.set(andis, card);}
    public void addToPlayerHistory(PlayHistory newHistory){this.playerHistory.add(newHistory);}
    public void setUsername(String username){this.username = username;}
    public void setNickname(String nickname){this.nickname=nickname;}
    public void setPassword(String password){this.password=password;}
    public void setEmail(String email){this.email=email;}
    public void setSecurityQuestion(String question){this.securityQuestion=question;}
    public void setSecurityAnswer(String answer){this.securityAnswer=answer;}
    public void setLevel(double level){this.level=level;}
    public void setHP(int HP){this.HP=HP;}
    public void setCoin(int coin){this.coin=coin;}


    public void showProfile(Perform perform){
        Perform.clearScreen();
        System.out.println("Nickname: " + this.getNickname() + "\n");
        System.out.printf("LEVEL: %03.0f\t\tTo next level: " , this.getLevel());
        int intlvl = (int)this.getLevel();
        double lvl = this.getLevel()-(int)this.getLevel();

        //print box for display how to level-up
        for(int i=1;i<21;i++)
        {
            if(lvl<(double) i/20)
                System.out.print("▱");
            else
                System.out.print("▰");
        }

        System.out.printf("\nCoin : " + this.getCoin()+"\t\tHP : %03d\n",this.getHP());
        System.out.println("\nUsername: " + this.getUsername() + "\t\tEmail: " + this.getEmail() );
        System.out.println("Password: " + this.getPassword() + "\n");
        System.out.println("Security question: " + this.getSecurityQuestion());
        System.out.println("\t-" + this.getSecurityAnswer() );

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
            }else if (inputLine.matches(Commands.getRegex(Commands.CHANGE_USERNAME))) {
                try{
                    matcher = Commands.getMatcher(inputLine, Commands.CHANGE_USERNAME);
                    String newUsername= matcher.group("newUsername").trim();
                    if(perform.getUsersHashMap().containsKey(newUsername)) {
                        System.out.println("This username already taken!\nPlease choose one else.");
                        Perform.wait(3);
                        Perform.clear(53+inputLine.length()+1);
                    }
                    else
                    {
                        File userFile = new File ("C://OOP_Project//Users//"+this.getUsername());
                        File newUserFile = new File ("C://OOP_Project//Users//"+ newUsername);
                        this.setUsername(newUsername);
                        if(userFile.renameTo(newUserFile))
                        {
                            System.out.println("Profile changed sucessfully!");
                            Perform.wait(3);
                            this.storageInformation();
                            this.showProfile(perform);
                            break;
                        }else{
                            System.out.println("Change profile failed!");
                            Perform.wait(3);
                            Perform.clear(23+inputLine.length()+1);
                        }
                    }
                }catch(Exception e)
                {
                    OpenCloseProgcessing.programWasEndInTen("Change username failed!");
                }
            }else if (inputLine.matches(Commands.getRegex(Commands.CHANGE_NICKNAME))) {
                try{
                    matcher = Commands.getMatcher(inputLine, Commands.CHANGE_NICKNAME);
                    this.setNickname(matcher.group("newNickname").trim());
                    this.storageInformation();
                    this.showProfile(perform);
                    break;
                }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Change nickname failed!");}

            }else if (inputLine.matches(Commands.getRegex(Commands.CHANGE_PASSWORD))) {
                try{
                    matcher = Commands.getMatcher(inputLine, Commands.CHANGE_PASSWORD);
                    String password= matcher.group("oldPassword").trim();
                    String newPassword= matcher.group("newPassword").trim();
                    while(!password.equals(this.getPassword()))
                    {
                        System.out.println("Password was wrong!\nPlease try again!");
                        Perform.wait(3);
                        Perform.clear(38);
                        password = new Scanner(System.in).nextLine().trim();
                    }
                    while(newPassword.equals(password))
                    {
                        System.out.println("Please enter new password!");
                        Perform.wait(3);
                        Perform.clear(27);
                        newPassword = new Scanner(System.in).nextLine().trim();
                    }
                    while(newPassword.length()<8)
                    {
                        System.out.println("Password must have at least 8 characters!\nPlease enter new password again:");
                        Perform.wait(3);
                        Perform.clear(75);
                        newPassword = new Scanner(System.in).nextLine().trim();
                    }
                    while(!password.matches("\\S*[a-z]\\S*") ||!password.matches("\\S*[a-z]\\S*")
                            || !password.matches("\\S*[!@#$%^&*()_+]\\S*")) {
                        System.out.println("Password must have at least 1 little, big and !@#$%^&*()_+ characters!");
                        System.out.println("Please enter new password again:");
                        Perform.wait(3);
                        Perform.clear(94);
                        newPassword = new Scanner(System.in).nextLine().trim();
                    }
                    this.setPassword(newPassword);
                    this.storageInformation();
                    this.showProfile(perform);
                    break;
                }catch(Exception e){
                    OpenCloseProgcessing.programWasEndInTen("Change password failed!");
                }

            }else if (inputLine.matches(Commands.getRegex(Commands.CHANGE_EMAIL))) {
                try{
                    matcher = Commands.getMatcher(inputLine, Commands.CHANGE_EMAIL);
                    String newEmail= matcher.group("newEmail").trim();
                    while(!email.matches("([\\w]+)[@]([\\w]+)(\\.com)")){
                        System.out.println("Email address was invalid!\nPlease enter email again:");
                        Perform.wait(3);
                        Perform.clear(48);
                        newEmail = new Scanner(System.in).nextLine().trim();
                    }
                    this.setEmail(newEmail);
                    this.storageInformation();
                    this.showProfile(perform);
                    break;
                }catch(Exception e){
                    OpenCloseProgcessing.programWasEndInTen("Change email failed!");
                }

            }else{
                Perform.invalidCommand();
            }
        }

    }

    public void storageDetails() {
        try{
            File userFile = new File("C://OOP_Project//Users//" + this.username);
            BufferedWriter informationFileWriter = new BufferedWriter(new FileWriter(userFile.getPath()+"Information.txt"));
            BufferedWriter playerHistoryFileWriter = new BufferedWriter(new FileWriter(userFile.getPath()+"PlayerHistoryFileWriter.txt"));
            BufferedWriter userCardsWriter = new BufferedWriter(new FileWriter(userFile.getPath()+"UserCards.txt"));

            //clean file from older data
            informationFileWriter.write("");
            playerHistoryFileWriter.write("");
            userCardsWriter.write("");

            //write new data now
            informationFileWriter.write(this.username + "\n" + this.password + "\n");
            informationFileWriter.write(this.nickname + "\n" + this.email + "\n");
            informationFileWriter.write(this.securityQuestion + "\n" + this.securityAnswer + "\n");
            informationFileWriter.write(this.level + "\n" + this.HP + "\n");
            informationFileWriter.write(this.coin);
            for(int i=0 ; i<this.playerHistory.size(); i++)
            {
                playerHistoryFileWriter.write(this.playerHistory.get(i).getHistory()+"\n");
            }
            for(int i=0 ; i<this.cards.size(); i++)
            {
                userCardsWriter.write(this.cards.get(i).getCardString()+"\n");
            }



        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Storage user data has a problem");}
    }

    public void storageInformation() {
        try{
            File userFile = new File("C://OOP_Project//Users//" + this.username);
            BufferedWriter informationFileWriter = new BufferedWriter(new FileWriter(userFile.getPath()+"Information.txt"));

            //clean file from older data
            informationFileWriter.write("");

            //write new data now
            informationFileWriter.write(this.username + "\n" + this.password + "\n");
            informationFileWriter.write(this.nickname + "\n" + this.email + "\n");
            informationFileWriter.write(this.securityQuestion + "\n" + this.securityAnswer + "\n");
            informationFileWriter.write(this.level + "\n" + this.HP + "\n");
            informationFileWriter.write(this.coin);

        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Storage user information failed!");}
    }

    public void storageCards(){
        try{
            File userFile = new File("C://OOP_Project//Users//" + this.username);
            BufferedWriter CardsFileWriter = new BufferedWriter(new FileWriter(userFile.getPath()+"Information.txt"));

            //clean file from older data
            CardsFileWriter.write("");

            //write new data now
            for(int i=0;i<this.getCards().size();i++)
            {
                CardsFileWriter.write(this.cards.get(i).getCardString()+"\n");
            }

        }catch(Exception e){OpenCloseProgcessing.programWasEndInTen("Storage user Cards failed!");}
    }




    User(String username, String password, String nickname, String email,
         String sequrityQuestion, String sequrityAnswer ,ArrayList<Card> cards,
        ArrayList<PlayHistory> playerHistory)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.securityQuestion = sequrityQuestion;
        this.securityAnswer = sequrityAnswer;
        this.cards = new ArrayList<>(cards);
        this.playerHistory = new ArrayList<>(playerHistory);
        this.level = 1.0;
        this.HP = beginnerHP;
        this.coin = beginnerCoin;

    }

    User(String username, String password, String nickname, String email,Perform perform)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.securityQuestion = new String();
        this.securityAnswer = new String();
        this.cards = (ArrayList<Card>) perform.getBeginnerCards().clone();
        this.playerHistory = new ArrayList<>();
        this.level = 1.0;
        this.HP = beginnerHP;
        this.coin = beginnerCoin;

    }

    User(String username, String password, String nickname, String email,
         String sequrityQuestion, String sequrityAnswer ,ArrayList<Card> cards,
         ArrayList<PlayHistory> playerHistory, double level, int HP, int coin)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.securityQuestion = sequrityQuestion;
        this.securityAnswer = sequrityAnswer;
        this.cards = new ArrayList<>(cards);
        this.playerHistory = new ArrayList<>(playerHistory);
        this.level = level;
        this.HP = HP;
        this.coin = coin;

    }


    User(File user) throws FileNotFoundException {
        File information = new File(user.getPath()+"//Information.txt");
        File playerHistory = new File(user.getPath()+"//PlayerHistory.txt");
        File userCards = new File(user.getPath()+"//UserCards.txt");

        try{
            FileReader readInfo= new FileReader(information);
            FileReader readPlayerHistory= new FileReader(information);
            Scanner scan = new Scanner(information);
            this.username = scan.nextLine();
            this.password = scan.nextLine();
            this.nickname = scan.nextLine();
            this.email = scan.nextLine();
            this.securityQuestion = scan.nextLine();
            this.securityAnswer = scan.nextLine();
            this.cards = new ArrayList<>();
            this.playerHistory = new ArrayList<>();
            this.level = scan.nextDouble();
            this.HP = scan.nextInt();
            this.coin = scan.nextInt();
            scan = new Scanner(playerHistory);
            while(scan.hasNext())
            {
                this.playerHistory.add(new PlayHistory(scan.nextLine(), this.username));
            }

            scan = new Scanner(userCards);
            while(scan.hasNext())
            {
                this.cards.add(new Card(scan.nextLine(), this.username));
                //past line add cards (i)th by read information from (i)th line of UserCards.txt
            }

        }
        catch (Exception e){OpenCloseProgcessing.programWasEndInTen("Create new User(File) has a problem");}




    }
}
