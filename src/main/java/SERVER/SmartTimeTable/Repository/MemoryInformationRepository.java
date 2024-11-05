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

    @Override
    public void save(Information information) {
        if (information == null || information.getName() == null) {
            throw new IllegalArgumentException("정보 또는 이름이 null일 수 없습니다.");
        }
        informationMap.put(information.getName(), information);
    }

    @Override
    public Optional<Information> findByName(String name) {
        return Optional.ofNullable(informationMap.get(name));
    }

    @Override
    public List<Information> findAll() {
        return new ArrayList<>(informationMap.values());
    }

    @Override
    public void update(String name, Information updatedInformation) {
        if (name == null || updatedInformation == null) {
            throw new IllegalArgumentException("이름 또는 업데이트할 정보가 null일 수 없습니다.");
        }
        informationMap.put(name, updatedInformation);
    }

    @Override
    public void delete(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름이 null일 수 없습니다.");
        }
        informationMap.remove(name);
    }
    @PostConstruct
    public void fetchContestData() {
        //os용
        System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.campuspick.com/contest");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Contest IDs 목록
            String[] contestIds = {"26901", "27036", "27164", "26903", "27068"};

            for (String contestId : contestIds) {
                // 요소 찾기
                WebElement figureElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//a[contains(@href, '/contest/view?id=" + contestId + "')]/figure)[1]")));

                // data-image 속성 값 가져오기
                String dataImage = figureElement.getAttribute("data-image");
                System.out.println("data-image 속성 값: " + dataImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 드라이버 종료
            driver.quit();
        }
    }


}
