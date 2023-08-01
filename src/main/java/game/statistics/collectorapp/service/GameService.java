package game.statistics.collectorapp.service;

import game.statistics.collectorapp.model.Game;

import java.util.List;
import java.util.Set;

public interface GameService {
    public Game saveGame(Game game);

    public Game updateGameScore(String gameName, String finalScore);

    public boolean isExists(String gameName);

    public Set<String> getGamesFromTheLastTwoHours();

    public Game getCurrentGameById(long id);

    public List<Game> getAllGames();


}
