package game.statistics.collectorapp.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class SeleniumConfiguration {

    private String url = "https://betcity.ru/ru/live/soccer";

//     @Bean
//     public FirefoxOptions firefoxOptions(){
//         FirefoxOptions options = new FirefoxOptions();
//         options.addArguments("-headless");
////         options.setHeadless(true);
//         return options;
//     }
//
//     @Bean
//     public WebDriverManager webDriverManager(FirefoxOptions firefoxOptions) {
//         WebDriverManager wdm = WebDriverManager.firefoxdriver().capabilities(firefoxOptions);
//         wdm.setup();
//         return wdm;
//     }


    @Bean
    public WebDriver driver() {
//        WebDriverManager wdm =  WebDriverManager.chromedriver().capabilities(new ChromeOptions()
//                .setBinary(System.getenv("GOOGLE_CHROME_SHIM"))
//                .addArguments("--remote-allow-origins=*")
//                .addArguments("--headless=new")
//                .addArguments("--process-per-site")
//                .addArguments("--use-gl=egl")
//                .addArguments("--disable-extensions")
//                .addArguments("--disable-dev-shm-usage")
//                .addArguments("--no-sandbox")
//                .addArguments("--incognito")
//        );

//mozilla
//        WebDriverManager wdm =  WebDriverManager.firefoxdriver()
//                .capabilities(new FirefoxOptions());
    //                        .addArguments("-remote-debugging-port=9224")
    //                        .addArguments("-headless"));

//chrome
//        WebDriverManager wdm =  WebDriverManager.chromedriver().capabilities(new ChromeOptions()
////                .addArguments("--headless")
//                .addArguments("--remote-allow-origins=*"));
//        wdm.setup();
//        WebDriver webDriver = wdm.create();
//        FirefoxOptions firefoxOptions = new FirefoxOptions()
//                .setBinary(System.getenv("FIREFOX_BIN"))
//                .addArguments("-headless");
////                .addArguments("-disable-gpu")
////                .addArguments("-no-sandbox");
//
//        System.setProperty("webdriver.gecko.driver", System.getenv("GECKODRIVER_PATH"));
//        WebDriver webDriver = new FirefoxDriver(firefoxOptions);


////  --- WORKED SCHEMA FOR CHROME ---
        ChromeOptions chromeOptions = new ChromeOptions()
                .setBinary(System.getenv("GOOGLE_CHROME_BIN"))
                .addArguments("--remote-allow-origins=*")
                .addArguments("--headless")
                .addArguments("--process-per-site")
                .addArguments("--use-gl=egl");
//                .addArguments("--disable-extensions")
//                .addArguments("--disable-dev-shm-usage")
//                .addArguments("--no-sandbox")
//                .addArguments("--incognito");
    

        System.setProperty("webdriver.gecko.driver", System.getenv("CHROMEDRIVER_PATH"));
        WebDriver webDriver = new ChromeDriver(chromeOptions);
//  ---------------------------------
        webDriver.get(url);
        return webDriver;
    }
}
