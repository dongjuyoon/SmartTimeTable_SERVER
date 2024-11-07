package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Subject;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemorySubjectRepository implements SubjectRepository {
    private final Map<String, Subject> subjectMap = new HashMap<>();
    private final Map<String, Subject> majorsMap = new HashMap<>();
    private final Map<String, Subject> coreElectivesMap = new HashMap<>();
    private final Map<String, Subject> commonElectivesMap = new HashMap<>();

    @Override
    public Subject findByname(String name) {
        return subjectMap.get(name);
    }

    @Override
    public Subject findByLectureNumber(String lectureNumber) {
        return subjectMap.get(lectureNumber);
    }

    @Override
    public Subject findByProfessor(String professor) {
        return subjectMap.get(professor);
    }

    @Override
    public void save(Subject subject) {
        subjectMap.put(subject.getLectureNumber(), subject); // 강좌번호를 키로 사용
    }
////    @PostConstruct
////    public void fetchCourseData() {
////        //os용
////        //System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
////        //window용
////        System.setProperty("webdriver.chrome.driver", "C:\\Users\\82108\\Downloads\\chromedriver-win64(130.0.6723.31)\\chromedriver-win64\\chromedriver.exe");
////        ChromeOptions options = new ChromeOptions();
////        WebDriver driver = new ChromeDriver(options);
////        driver.get("https://lms.mju.ac.kr/ilos/st/main/course_ing_list_form.acl");
////
////        try {
////            int currentPage = 1;
////
////            while (true) {
////                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody tr")));
////
////                List<WebElement> elements = driver.findElements(By.cssSelector("tbody tr"));
////
////                if (elements.isEmpty()) {
////                    System.out.println("더 이상 로드할 데이터가 없습니다. 크롤링을 종료합니다.");
////                    break; // 반복문 종료
////                }
////
////                for (WebElement element : elements) {
////                    List<WebElement> columns = element.findElements(By.tagName("td"));
////                    if (!columns.isEmpty()) {
////                        String[] subjectInfo = columns.get(2).getText().split("\n");
////                        String subjectName = subjectInfo[0];
////                        String professorName = subjectInfo.length > 1 ? subjectInfo[1] : "";
////
////                        // Subject 객체 생성
////                        Subject subjectInfoObj = new Subject();
////                        subjectInfoObj.setLectureNumber(columns.get(0).getText()); // 강좌번호
////                        subjectInfoObj.setName(subjectName); // 강의명
////                        subjectInfoObj.setProfessor(professorName); // 교수명
////                        subjectInfoObj.setClassTime(columns.get(3).getText()); // 요일 및 강의 시간
////
////
////                        System.out.println(subjectInfoObj.getLectureNumber());
////                        System.out.println(subjectInfoObj.getName());
////                        System.out.println(subjectInfoObj.getProfessor());
////
////                        save(subjectInfoObj); // 과목 저장
////                    }
////                }
////
////                try {
////                    if(currentPage < 338) {
////                        if (currentPage % 10 != 0) {
////                            WebElement pageLink = driver.findElement(By.xpath("//a[contains(@href, \"javascript:listPage('" + (currentPage * 10 + 1) + "')\")]"));
////                            pageLink.click();
////                        } else {
////                            WebElement nextPageLink = driver.findElement(By.cssSelector("span.next a"));
////                            nextPageLink.click();
////                        }
////
////                        currentPage++;
////                        wait.until(ExpectedConditions.stalenessOf(elements.get(0)));
////                    } else{
////                        break;
////                    }
////                } catch (Exception e) {
////                    System.err.println("데이터 크롤링 중 오류 발생: " + e.getMessage());
////                    driver.quit(); // 드라이버 종료
/////*
////                    e.printStackTrace(); // 스택 트레이스를 출력하여 문제를 진단
////*/
////                }
////            }
////        } finally {
////            driver.quit(); // 드라이버 종료
////        }
//    }

//    public void addSubject(Subject subject) {
//        subjectMap.put(subject.getName(), subject);
//    }

    @Override
    public void updateSubject(String name, Subject updatedSubject) {
        subjectMap.put(name, updatedSubject);
    }

    @Override
    public void deleteSubject(String name) {
        subjectMap.remove(name);
    }

    @Override
    public List<Subject> findAll() {
        return new ArrayList<>(subjectMap.values());
    }

    @Override
    public List<Subject> findMajors(){
        return Majors;
    }

    @Override
    public List<Subject> findCoreElectives(){
        return CoreElectives;
    }

    @Override
    public List<Subject> findCommonElectives(){
        return CommonElectives;
    }

    public List<Subject> findAllElectives(){
        List<Subject> allElectives = new ArrayList<>();
        allElectives.addAll(findMajors());
        allElectives.addAll(findCoreElectives());
        allElectives.addAll(findCommonElectives());

        return allElectives;
    }
}