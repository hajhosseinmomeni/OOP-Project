package packs.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    END("\\s*end\\s*"),
    LOGIN_USER("\\s*user\\s+login\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*"),
    SIGNUP_USER("\\s*user\\s+create\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s+(?<passwordConfirmation>\\S+)\\s+(?<email>\\S+)\\s+(?<nickname>\\S+)\\s*"),
    SIGNUP_USER_WITH_RANDOM_PASSWORD("\\s*user\\s+create\\s+(?<username>\\S+)\\s+random\\s+(?<email>\\S+)\\s+(?<nickname>\\S+)\\s*"),
    USERNAME_FORMAT("[a-zA-Z]([\\w|]+)"),
    NEXT_PAGE("\\s*next\\s*"),
    ANDIS_PAGE("\\s*page\\s+(?<pageNumber>\\S+)\\s*"),
    PREVIOUS_PAGE("\\s*previous\\s*"),
    BACK_TO_MAIN_MENU("\\s*back\\s*"),
    SORT_OF_PLAYER_HISTORY("\\s*sort\\s+by\\s+(?<format>\\S+)\\s*"),
    REVERSE_SORT("\\s*reverse\\s+order\\s*"),
    CHANGE_USERNAME("\\s*Profile\\s+change\\s+(?<newUsername>\\S+)\\s*"),
    CHANGE_NICKNAME("\\s*Profile\\s+change\\s+nickname\\s+(?<newNickname>\\S+)\\s*"),
    CHANGE_PASSWORD("\\s*Profile\\s+change\\s+password\\s+(?<oldPassword>\\S+)\\s+(?<newPassword>\\S+)\\s*"),
    CHANGE_EMAIL("\\s*change\\s+email\\s+(?<newEmail>\\S+)\\s*"),
    UPGRADE_MY_CARDS("\\s*upgrade\\s+my\\s+cards\\s*"),
    BUY_SPECIAL_CARDS("\\s*buy\\s+special\\s+cards\\s*"),
    BUY_ADDED_CARDS("\\s*buy\\s+added\\s+cards\\s*"),
    LOG_OUT("\\s*log\\s+out\\s*"),
    SEE_PROFILE("\\s*see\\s+profile\\s*"),
    PLAY_GAME("\\s*start\\s+the\\s+game\\s*"),
    SEE_HISTORY("\\s*see\\s+history\\s*"),
    GO_TO_STORE("\\s*go\\s+to\\s+store\\s*"),
    WAR_BETWEEN("\\s*war\\s+between\\s+(?<country1>\\S+)\\s+and\\s+(?<country2>\\S+)\\s+in\\s+(?<place>\\S+)\\s*"),
    WAR_BETWEEN_WITH_NO_PLACE("\\s*war\\s+between\\s+(?<country1>\\S+)\\s+and\\s+(?<country2>\\S+)\\s*");


    private String regex;
    Commands (String regex) {
        this.regex = regex;
    }
    public static String getRegex (Commands commands) {
        return commands.regex;
    }
    public static Matcher getMatcher (String input, Commands commands) {
        Matcher matcher = Pattern.compile(commands.regex).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

