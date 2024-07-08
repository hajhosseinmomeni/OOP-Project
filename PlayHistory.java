package packs;
import packs.enums.Commands;

import java.util.*;
import java.time.*;
import java.time.format.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class historyData{
    boolean tartibBaraks = false;
    protected final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    protected String result;//win or lose
    protected String harif;//harif username
    protected double harifLevel;
    protected LocalDateTime timeObj;

}
interface historyMethod{
    public String getResult();
    public String getHarif();
    public Double getHarifLevel();
    public LocalDateTime getTimeObj();
    public String getHistory();

    public void printTime();
    public void printHarif();
    public void printHarifLevel();
    public void printResult();
    public void printHistory();

    //public static void printlnListHistory(ArrayList<PlayHistory> playerHistory, String format, boolean tartibBaraks);

}

public class PlayHistory extends historyData implements historyMethod{


    public String getResult(){return this.result;}
    public String getHarif(){return this.harif;}
    public Double getHarifLevel(){return this.harifLevel;}
    public LocalDateTime getTimeObj(){return this.timeObj;}
    public String getHistory(){return (this.timeObj.format(this.timeFormat)+ " you " + this.result + " in play with " + this.harif + " by level " + this.harifLevel);}

    static class ResultComparator implements Comparator<PlayHistory> {
        @Override
        public int compare(PlayHistory o1, PlayHistory o2) {
            return o1.getResult().compareTo(o2.getResult());
        }
    }
    static class HarifComparator implements Comparator<PlayHistory> {
        @Override
        public int compare(PlayHistory o1, PlayHistory o2) {
            return o1.getHarif().compareTo(o2.getHarif());
        }
    }
    static class HarifLevelComparator implements Comparator<PlayHistory> {
        @Override
        public int compare(PlayHistory o1, PlayHistory o2) {
            return o1.getHarifLevel().compareTo(o2.getHarifLevel());
        }
    }
    static class TimeObjComparator implements Comparator<PlayHistory> {
        @Override
        public int compare(PlayHistory o1, PlayHistory o2) {
            return o1.getTimeObj().compareTo(o2.getTimeObj());
        }
    }

    public void printTime(){System.out.print(this.timeObj.format(this.timeFormat));}
    public void printHarif(){System.out.print(this.harif);}
    public void printHarifLevel(){System.out.print(this.harifLevel);}
    public void printResult(){System.out.print(this.result);}
    public void printHistory(){System.out.print(this.timeObj.format(this.timeFormat)+ " you " + this.result + " in play with " + this.harif + " by level " + this.harifLevel);}
    public static void printlnListHistory(ArrayList<PlayHistory> playerHistory, String format, boolean tartibBaraks, int pageNumber)  {
        try{
            Perform.clearScreen();
            System.out.println("you can:\n-sort by [time/harifName/result/harifLevel]");
            System.out.println("-reverse the sort order (reverse order)");
            System.out.println("-reverse the sort order (reverse order)");
            System.out.println("-go next and previous page (next/previous)");
            System.out.println("-go (page [page-number])\n");

            if(pageNumber==0)
                System.out.println("This is first page of your history:");
            else if(pageNumber==playerHistory.size()/10)
                System.out.println("This is last page of your history:");
            switch (format){
                case "time":
                    Collections.sort(playerHistory , new TimeObjComparator());
                    break;
                case "harifName":
                    Collections.sort(playerHistory , new HarifComparator());
                    break;
                case "result":
                    Collections.sort(playerHistory , new ResultComparator());
                    break;
                case "harifLevel":
                    Collections.sort(playerHistory , new HarifLevelComparator());
                    break;
            }
            if(tartibBaraks)
                Collections.reverse(playerHistory);
            for(int i=10*pageNumber; i<10*pageNumber+10; i++)
            {
                if(i>=playerHistory.size())
                    break;
                playerHistory.get(i).printHistory();
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
                }else if (inputLine.matches(Commands.getRegex(Commands.NEXT_PAGE))) {
                    if(pageNumber!=playerHistory.size()/10)
                        PlayHistory.printlnListHistory(playerHistory, format, tartibBaraks, pageNumber+1);
                    break;
                }else if (inputLine.matches(Commands.getRegex(Commands.PREVIOUS_PAGE))) {
                    if(pageNumber!=0)
                        PlayHistory.printlnListHistory(playerHistory, format, tartibBaraks, pageNumber-1);
                    break;
                }else if (inputLine.matches(Commands.getRegex(Commands.ANDIS_PAGE))) {
                    matcher = Commands.getMatcher(inputLine, Commands.ANDIS_PAGE);
                    int newPageNumber= Integer.valueOf(matcher.group("pageNumber").trim());
                    if(newPageNumber>=0 && newPageNumber<=playerHistory.size()/10)
                        PlayHistory.printlnListHistory(playerHistory, format, tartibBaraks, newPageNumber);
                    break;
                }else if (inputLine.matches(Commands.getRegex(Commands.SORT_OF_PLAYER_HISTORY))) {
                    matcher = Commands.getMatcher(inputLine, Commands.SORT_OF_PLAYER_HISTORY);
                    String newFormat= (matcher.group("pageNumber").trim());
                    if(newFormat.equals(format))
                        PlayHistory.printlnListHistory(playerHistory, newFormat, !tartibBaraks, pageNumber);
                    else
                        PlayHistory.printlnListHistory(playerHistory, newFormat, tartibBaraks, pageNumber);
                    break;
                }else if (inputLine.matches(Commands.getRegex(Commands.REVERSE_SORT))) {
                    PlayHistory.printlnListHistory(playerHistory, format, !tartibBaraks, pageNumber);
                    break;
                }else{
                    Perform.invalidCommand();
                }
            }
        }catch(Exception e){
            OpenCloseProgcessing.programWasEndInTen("Display history failed!");
        }

    }


    public PlayHistory(String input , String username) {
        //yyyy.MM.dd hh:mm you (win/lose) in play with user2 by level user2Level
        String regex = "\\s*(?<TimeObj>\\S+)\\s+you\\s+(?<Result>\\S+)\\s+in play with\\s+(?<Harif>\\S+)\\s+by level\\s+(?<HarifLevel>\\S+)\\s*";
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (!matcher.matches())
            OpenCloseProgcessing.programWasEndInTen("Something in PlayerHistory of User: " + username + " is wrong");
        this.result = matcher.group("Result").trim();//win or lose
        this.harif= matcher.group("Harif").trim();//harif username
        this.harifLevel= Double.valueOf(matcher.group("HarifLevel").trim());
        this.timeObj= LocalDateTime.parse(matcher.group("TimeObj").trim(), this.timeFormat);
    }


}
