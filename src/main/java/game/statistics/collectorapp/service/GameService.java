package game.statistics.collectorapp.service;

import game.statistics.collectorapp.model.Game;

import java.util.List;

public interface GameService {
    public Game saveGame(Game game);

    public Game updateGameScore(String gameName, String finalScore);

    public boolean isExists(String gameName);

    public Game getCurrentGameById(long id);

    public List<Game> getAllGames();


}
