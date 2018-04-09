import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *The method is used to be in Interaction with user.
 * @container keeps returned recipes based on user`s input
 * @commands keeps answer(s)
 *
 */

public class TalkingBot extends TelegramLongPollingBot {

    static ArrayList<String> container = new ArrayList<>();

    public static void contain(String input) {

        container.add(input);
    }

    @Override
    public void onUpdateReceived(Update update) { //TODO exception handling by undefined ingredient input

        HashMap<String, String> commands = new HashMap<>();
        commands.put("/start", "Welcome to the ChiefAdviser." +
                " To get recipe please type the ingredient(s) you have.");
        long id = update.getMessage().getChatId(); //unique id number of user.
        String commandsList = "/start next";

        if (update.hasMessage()) {

            String query = update.getMessage().getText(); //telegram user`s input
            String text = commands.get(query); //message text has to be sent to telegram user

            if (commandsList.contains(query) && !query.equals("next")) {
                SendMessage message = new SendMessage()
                        .setChatId(id)
                        .setText(text);
                System.out.println("1:\n" + text);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (!commandsList.contains(query)) {
                Recipe.get(query); //triggers the method to get the recipes based on input of user
                text = container.get(0) + "\nto see another recipe please type next"; //returns the first recipe in the list
                SendMessage message = new SendMessage()
                        .setChatId(id)
                        .setText(text);
                System.out.println("2:\n" + text);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                container.remove(0); //deleting already sent recipe to keep recipe list fresh
            }
            if (query.equals("next")) {

                text = container.get(0) + "\nto see another recipe please type next"; //returns the first recipe in the list
                SendMessage message = new SendMessage()
                        .setChatId(id)
                        .setText(text);
                System.out.println("3:\n" + text);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                container.remove(0); //deleting already sent recipe to keep recipe list fresh
            }
        }
    }

    @Override
    public String getBotToken() {
        return "463701041:AAFhF6Sr_ziA558Hxa7YzJKiAbRV1Ni83-Q";
    }

    @Override
    public String getBotUsername() {
        return "ChiefAdviserBot";
    }
}