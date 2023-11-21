package game.statistics.collectorapp.controller;

import game.statistics.collectorapp.bot.FootballStatisticsBot;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebhookController {

    private final FootballStatisticsBot footballStatisticsBot;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return footballStatisticsBot.onWebhookUpdateReceived(update);
    }
}
