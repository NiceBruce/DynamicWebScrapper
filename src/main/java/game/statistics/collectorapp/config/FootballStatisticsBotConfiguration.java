package game.statistics.collectorapp.config;

import game.statistics.collectorapp.bot.FootballStatisticsBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class FootballStatisticsBotConfiguration {
    private String botHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public FootballStatisticsBot footballStatisticsBot() {

        FootballStatisticsBot footballStatisticsBot = new FootballStatisticsBot();
        footballStatisticsBot.setBotUserName(botUserName);
        footballStatisticsBot.setBotToken(botToken);
        footballStatisticsBot.setBotHookPath(botHookPath);

        return footballStatisticsBot;
    }
}
