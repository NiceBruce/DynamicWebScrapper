package game.statistics.collectorapp.job;

import game.statistics.collectorapp.model.Game;
import game.statistics.collectorapp.repository.GameRepository;
import game.statistics.collectorapp.service.GameService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class ParseTask {

    static ParserUtil util = new ParserUtil();

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GameService gameService;

    @Autowired
    WebDriver driver;

    @Scheduled(fixedDelay = 1000)
    public void parseGames() throws URISyntaxException, UnsupportedEncodingException {
        List<WebElement> games = util.getGames(driver);

        for (WebElement game : games) {
            if (game.isDisplayed()) {
                String name = util.getGameName(game);

                if (gameService.isExists(name) && util.getStatusGame(game)) {
                    gameService.updateGameScore(name, util.getGameScore(game));
                } else {
                    String timer = util.getGameTimer(game);
                    String score = util.getGameScore(game);
                    String linkToStatistics = util.getLinkToStatistics(game);

                    if (util.isValidGame(timer, name, score, linkToStatistics)) {
                        if (!gameService.isExists(name)) {
                            gameService.saveGame(Game.builder()
                                    .name(name)
                                    .startScore(score)
                                    .timer(timer)
                                    .link(util.getCurrentLinkToGame(game))
                                    .tot_koef(util.getCoefficientTOT(game))
                                    .b_koef(util.getCoefficientB(game))
                                    .build());

                            util.sendToTelegramm(game);
                        }
                    }
                }
            }
        }
    }
}