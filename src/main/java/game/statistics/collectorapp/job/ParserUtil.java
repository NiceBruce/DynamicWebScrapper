package game.statistics.collectorapp.job;

import game.statistics.collectorapp.model.Game;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ParserUtil {

    private HttpClient httpClient = HttpClient.newHttpClient();

    public static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    public boolean checkGoals(String owner, String guest, String linkToStatistics, int goals) {
        int countOwnerGoals = 0;
        int countGuestGoals = 0;

        Document doc  = null;
        try {
            doc = Jsoup.connect(linkToStatistics).get();
        } catch (IOException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ ПРЕДСТАВЛЕНА СТАТИСТИКА ПО ИГРАМ", new RuntimeException(exceptionMessage));
            return false;
        }

        List<Element> lastGameTables = doc.getElementsByClass("ev-mstat-tbl");

        if (lastGameTables.size() == 0) {
            LOGGER.error("ВНИМАНИЕ! НЕ ПРЕДСТАВЛЕНА СТАТИСТИКА ПО ИГРАМ");
            return false;
        }

        try {
            List<Element> ownerTable = lastGameTables.get(0).getElementsByTag("tr");
            for (Element row : ownerTable) {
                if (row.select("td a").size() != 0) {
                    if (row.getElementsByTag("td").get(0).getElementsByTag("a").text().split("-")[0].trim().equals(owner)) {
                        countOwnerGoals += Integer.parseInt(row.getElementsByTag("td").get(1).text().substring(0,3).split(":")[0]);
                    } else {
                        countOwnerGoals += Integer.parseInt(row.getElementsByTag("td").get(1).text().substring(0,3).split(":")[1]);
                    }
                }
            }
        } catch (IndexOutOfBoundsException exceptionMessage) {
            LOGGER.error(String.format("ВНИМАНИЕ! ПРЕДСТАВЛЕНА НЕ ПОЛНАЯ СТАТИСТИКА ПО ИГРАМ - ОТСУТСТВУЮТ ДАННЫЕ ПО ДОМАШНЕЙ КОМАНДЕ %s", owner));
            return false;
        }

        try {
            List<Element> guestTable = lastGameTables.get(1).getElementsByTag("tr");
            for (Element row : guestTable) {
                if (row.select("td a").size() != 0) {
                    if (row.getElementsByTag("td").get(0).getElementsByTag("a").text().split("-")[0].trim().equals(guest)) {
                        countGuestGoals += Integer.parseInt(row.getElementsByTag("td").get(1).text().substring(0,3).split(":")[1]);
                    } else {
                        countGuestGoals += Integer.parseInt(row.getElementsByTag("td").get(1).text().substring(0,3).split(":")[0]);
                    }
                }
            }
        } catch (IndexOutOfBoundsException exceptionMessage) {
            LOGGER.error(String.format("ВНИМАНИЕ! ПРЕДСТАВЛЕНА НЕ ПОЛНАЯ СТАТИСТИКА ПО ИГРАМ - ОТСУТСТВУЮТ ДАННЫЕ ПО ГОСТЕВОЙ КОМАНДЕ %s", guest));
            return false;
        }

        return (countOwnerGoals  >= goals && countGuestGoals >= goals);
    }

    public boolean getStatusGame(WebElement game) {
        try {
            return game.findElement(By.tagName("app-event-status-message")).findElement(By.tagName("span")).getText().equals("Приём пари временно остановлен");
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ВЭБ-ЭЛЕМЕНТ ОКОНЧАНИЯ ИГРЫ: %s".formatted(getGameName(game)));
            return false;
        }
    }

    public List <WebElement>getGames(WebDriver driver) {
        List<WebElement> games = new ArrayList<>();

        try {
//            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//            games = new WebDriverWait(driver, Duration.ofMillis(3000))
//                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("line__champ")));
            games = driver.findElements(By.className("line__champ"));
//            games = driver.findElements(By.tagName("app-event-unit"));
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕНЫ ВЭБ-ЭЛЕМЕНТЫ ИГР", new Exception(exceptionMessage));
        }

        return games;
    }

    public String getGameName(WebElement game) {
        String gameName = "";
        try {
            gameName = game.findElement(By.className("line-event__name")).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ВЭБ-ЭЛЕМЕНТ НАЗВАНИЯ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return gameName;
    }

    public String getOwnerName(String gameName) {
        return gameName.split("\\n")[0];
    }

    public String getGuestName(String gameName) {
        return gameName.split("\\n")[1];
    }

    public String getGameScore(WebElement game) {
        String gameScore = "";
        try {
            gameScore = game.findElement(By.className("line-event__score-total")).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ВЭБ-ЭЛЕМЕНТ СЧЕТА ИГРЫ: %s".formatted(getGameName(game)));
        }

        return gameScore;
    }

    public String getGameTimer(WebElement game) {
        String gameTimer = "";
        try {
            gameTimer = game.findElement(By.className("line-event__time-timer")).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ВЭБ-ЭЛЕМЕНТ ВРЕМЕНИ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return gameTimer;
    }

    public String getLeague(WebElement game) {
        String league = "";
        try {
            league = game.findElement(By.className("line-champ__header-link")).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ВЭБ-ЭЛЕМЕНТ ЛИГИ ТЕКУЩЕЙ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return league;
    }

    public String getLinkToStatistics(WebElement game) {
        String linkToStatistics = "";
        try {
            linkToStatistics = game.findElement(By.tagName("app-mstat-button")).findElement(By.xpath(".//a")).getAttribute("href");
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕНА ССЫЛКА НА СТАТИСТИКУ ИГРЫ - %s".formatted(getGameName(game)));
        }

        return linkToStatistics;
    }

    public String getCurrentLinkToGame(WebElement game) {
        String currentLinkToGame = "";
        try {
            currentLinkToGame = game.findElement(By.className("line-event__name")).findElement(By.tagName("a")).getAttribute("href");
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕНА ССЫЛКА НА СТРАНИЦУ ТЕКУЩЕЙ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return currentLinkToGame;
    }

    public String getCoefficientTOT(WebElement game) {
        String totKoef = "";
        try {
            totKoef = game.findElement(By.tagName("app-main-dops-container"))
                    .findElement(By.className("line-event__main-bets"))
                    .findElements(By.xpath(".//*"))
                    .get(7).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН ТОТ-КОЭФИЦИЕНТ ТЕКУЩЕЙ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return totKoef;
    }

    public String getCoefficientB(WebElement game) {
        String coefficientB = "";
        try {
            coefficientB = game.findElement(By.tagName("app-main-dops-container"))
                    .findElement(By.className("line-event__main-bets"))
                    .findElements(By.xpath(".//*"))
                    .get(9).getText();
        } catch (NoSuchElementException exceptionMessage) {
            LOGGER.error("ВНИМАНИЕ! НЕ НАЙДЕН Б-КОЭФИЦИЕНТ ТЕКУЩЕЙ ИГРЫ: %s".formatted(getGameName(game)));
        }

        return coefficientB;
    }

    public boolean isNotCyberAndStatisticsGame(String legue) {
        return (!legue.toLowerCase().contains("киберфутбол") && !legue.toLowerCase().contains("статистика"));
    }
    public boolean isValidGame(String gameTimer, String gameName, String gameScore, String linkToStatistics) {
        return (gameTimer.length() != 0 && !gameTimer.equals("Перерыв"))
//                && (Integer.parseInt(gameTimer.substring(0, 2)) <= 70) && gameScore.equals("0:0")
                && (((Integer.parseInt(gameTimer.substring(0, 2)) >= 65) && (Integer.parseInt(gameTimer.substring(0, 2)) <= 70)) && gameScore.equals("0:0"))
                && checkGoals(getOwnerName(gameName), getGuestName(gameName), linkToStatistics, 10);
    }

    public boolean isElementDisplayed(WebElement game) {
        try {
            if (game.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
//            if (e instanceof StaleElementReferenceException) {
//                LOGGER.error("ВНИМАНИЕ! ЭЛЕМЕНТ ИГРЫ НЕ ПРЕДСТАВЛЕН В DOM! - %s".formatted(e.getMessage()));
//            } else if (e instanceof WebDriverException) {
//                LOGGER.error("ВНИМАНИЕ! ЭЛЕМЕНТ ИГРЫ НЕ ПРЕДСТАВЛЕН В DOM! - %s".formatted(e.getMessage()));
//            }

            return false;
        }
    }

    public String printCurrentGame(WebElement game) throws UnsupportedEncodingException {
        String gameName = getGameName(game);
        String timer = getGameTimer(game);
        String score = getGameScore(game);
        String link = getCurrentLinkToGame(game);

        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println(timer + " " + score + " " + getOwnerName(gameName) + "-" + getGuestName(gameName) + " " + link);
        System.out.println("________________________________________________________________________________________________________________________");
        return URLEncoder.encode("Найдена новая игра: " +getOwnerName(gameName) + " - " + getGuestName(gameName) + '\n'
                + "Время игры " + timer + " | счет: " + score + '\n'
                + "ТОТ-коэффициент: " + getCoefficientTOT(game) + ", Б-коэффициент: " + getCoefficientB(game) + '\n'
                + link,  "UTF-8");
    }

    public void sendToTelegramm(WebElement game) throws URISyntaxException, UnsupportedEncodingException {

        HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.telegram.org/bot1114352987:AAG8IzQuuMd8qIzEbcNEuaZFqQX5ifsEBI0/sendMessage?chat_id=422301922&text=%s".formatted(printCurrentGame(game))))
                        .GET()
                        .build();

        HttpRequest request2 = HttpRequest.newBuilder()
                        .uri(new URI("https://api.telegram.org/bot1114352987:AAG8IzQuuMd8qIzEbcNEuaZFqQX5ifsEBI0/sendMessage?chat_id=310019396&text=%s".formatted(printCurrentGame(game))))
                        .GET()
                        .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            httpClient.sendAsync(request2, HttpResponse.BodyHandlers.ofString());
    }
}
