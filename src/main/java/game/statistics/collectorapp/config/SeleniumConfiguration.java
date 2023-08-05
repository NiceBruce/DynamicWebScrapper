package game.statistics.collectorapp.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration {

    private String url = "https://betcity.ru/ru/live/soccer";

    @Bean
    public WebDriver driver() {

        WebDriverManager wdm =  WebDriverManager.chromedriver().capabilities(new ChromeOptions()
                        .addArguments("--remote-allow-origins=*")
//                        .addArguments("--process-per-site")
//                        .addArguments("--ignore-certificate-errors")
//                        .addArguments("--disable-extensions")
//                        .addArguments("--disable-dev-shm-usage")
//                        .addArguments("--use-gl=egl")
//                        .addArguments("--no-sandbox")
//                        .addArguments("--test-type")
//                        .addArguments("--headless")
//                        .addArguments("--incognito")
//                        .addArguments("--disk-cache-size=0")
//                        .addArguments("--verbose")
        );

        wdm.setup();
        WebDriver webDriver = wdm.create();
        webDriver.get(url);
        return webDriver;
    }
}
