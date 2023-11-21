package game.statistics.collectorapp.bot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class FootballStatisticsBot extends TelegramWebhookBot {

    private static final String TELEGRAMM_USER_ID = "YOU_TELEGRAMM_ID"; // Type your telegrammId here
    private String botHookPath;
    private String botUserName;
    private String botToken;


    public void sendMessage(String text) {
        var sendMessage = new SendMessage(TELEGRAMM_USER_ID, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            try {
                execute(new SendMessage(TELEGRAMM_USER_ID, update.getMessage().getText()));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public String getBotPath() {
        return botHookPath;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotHookPath(String botHookPath) {
        this.botHookPath = botHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
