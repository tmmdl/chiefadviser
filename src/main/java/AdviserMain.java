import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * The ChiefAdviser program is used to return proper food recipe to user.
 * The recipe(s) are collected from database based on user`s inputs.
 * In this program inputs come from Telegram Bot called ChiefAdviserBot.
 */

public class AdviserMain {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new TalkingBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
