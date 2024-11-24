package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Information;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryInformationRepository implements InformationRepository {
    private final Map<String, Information> informationMap = new HashMap<>();
    private List<String> dataImages = new ArrayList<>(); // data-image 속성을 저장할 리스트

    @PostConstruct
    public void fetchContestData() {
        // os용
        System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.campuspick.com/contest");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Contest ID가 아닌 패턴 기반으로 요소 찾기
            String[] contestPatterns = {"view?id=", "contest"};

            for (String pattern : contestPatterns) {
                // 해당 패턴을 포함하는 요소를 찾기
                WebElement figureElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@href, '" + pattern + "')]/figure")));

                // data-image 속성 값 가져오기
                String dataImage = figureElement.getAttribute("data-image");
                System.out.println("data-image 속성 값: " + dataImage);
                dataImages.add(dataImage); // data-image 값을 리스트에 추가
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 드라이버 종료
            driver.quit();
        }
    }

    // data-image 값을 반환하는 메서드
    public List<String> getDataImages() {
        return new ArrayList<>(dataImages); // data-image 리스트 반환
    }
}
