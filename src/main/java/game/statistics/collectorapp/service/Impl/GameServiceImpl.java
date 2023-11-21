package game.statistics.collectorapp.service.Impl;

import game.statistics.collectorapp.model.Game;
import game.statistics.collectorapp.repository.GameRepository;
import game.statistics.collectorapp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository repository;
    @Override
    public Game saveGame(Game game) {
        return repository.save(game);
    }

    @Override
    public Game updateGameScore(String gameName, String finalScore) {
        Game game = repository.getGameByName(gameName);
        game.setEndScore(finalScore);
        repository.save(game);
        return game;
    }

    @Override
    public boolean isExists(String gameName) {
        return repository.existsGameByName(gameName);
    }

    @Override
    public Set<String> getGamesFromTheLastTwoHours() {
        List<Game> games =  getAllGames();
        return games.stream()
                .map(game -> game.getName())
                .collect(Collectors.toSet());
    }

    @Override
    public Game getCurrentGameById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public List<Game> getAllGames() {
        return repository.findAll();
    }
}
