package game.statistics.collectorapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameStatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameStatisticsApplication.class, args);
    }

}
