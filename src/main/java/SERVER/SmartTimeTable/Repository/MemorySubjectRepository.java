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
    @PostConstruct
    public void fetchCourseData() {
        //os용
        //System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
        //window용
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\82108\\Downloads\\chromedriver-win64(130.0.6723.31)\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://lms.mju.ac.kr/ilos/st/main/course_ing_list_form.acl");

        try {
            int currentPage = 1;

            while (true) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody tr")));

                List<WebElement> elements = driver.findElements(By.cssSelector("tbody tr"));

                if (elements.isEmpty()) {
                    System.out.println("더 이상 로드할 데이터가 없습니다. 크롤링을 종료합니다.");
                    break; // 반복문 종료
                }

                for (WebElement element : elements) {
                    List<WebElement> columns = element.findElements(By.tagName("td"));
                    if (!columns.isEmpty()) {
                        String[] subjectInfo = columns.get(2).getText().split("\n");
                        String subjectName = subjectInfo[0];
                        String professorName = subjectInfo.length > 1 ? subjectInfo[1] : "";

                        // Subject 객체 생성
                        Subject subjectInfoObj = new Subject();
                        subjectInfoObj.setLectureNumber(columns.get(0).getText()); // 강좌번호
                        subjectInfoObj.setName(subjectName); // 강의명
                        subjectInfoObj.setProfessor(professorName); // 교수명
                        subjectInfoObj.setClassTime(columns.get(3).getText()); // 요일 및 강의 시간


                        System.out.println(subjectInfoObj.getLectureNumber());
                        System.out.println(subjectInfoObj.getName());
                        System.out.println(subjectInfoObj.getProfessor());

                          save(subjectInfoObj); // 과목 저장
                        if (subjectInfoObj.getName().contains("JEJ")) {
                            majorsMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("전공에 추가");
                        } else if (subjectInfoObj.getName().contains("철학과 인간") || subjectInfoObj.getName().contains("한국근현대사의 이해") || subjectInfoObj.getName().contains("역사와 문명") || subjectInfoObj.getName().contains("4차산업혁명을위한비판적사고") || subjectInfoObj.getName().contains("디지털콘텐츠로 만나는 한국의 문화유산") || subjectInfoObj.getName().contains("세계화와 사회변화") || subjectInfoObj.getName().contains("민주주의와 현대사회") || subjectInfoObj.getName().contains("창업입문") || subjectInfoObj.getName().contains("여성·소수자·공동체") || subjectInfoObj.getName().contains("현대사회와 심리학") || subjectInfoObj.getName().contains("직무수행과 전략적 의사소통") || subjectInfoObj.getName().contains("글로벌문화") || subjectInfoObj.getName().contains("고전으로읽는 인문학") || subjectInfoObj.getName().contains("예술과창조성") || subjectInfoObj.getName().contains("4차산업혁명시대의예술") || subjectInfoObj.getName().contains("문화리터러시와창의적스토리텔링") || subjectInfoObj.getName().contains("디지털문화의 이해") || subjectInfoObj.getName().contains("환경과 인간") || subjectInfoObj.getName().contains("우주,생명,마음") || subjectInfoObj.getName().contains("SW프로그래밍입문") || subjectInfoObj.getName().contains("인공지능의 세계") || subjectInfoObj.getName().contains("4차산업혁명의 이해") || subjectInfoObj.getName().contains("파이썬을활용한데이터분석과인공지능") || subjectInfoObj.getName().contains("외국인학생을위한컴퓨터활용")) {
                            coreElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("핵심에 추가");

                        } else if (subjectInfoObj.getName().contains("채플") || subjectInfoObj.getName().contains("성서와인간이해") || subjectInfoObj.getName().contains("현대사회와 기독교 윤리") || subjectInfoObj.getName().contains("종교와과학") || subjectInfoObj.getName().contains("기독교와문화") || subjectInfoObj.getName().contains("글쓰기") || subjectInfoObj.getName().contains("발표와토의") || subjectInfoObj.getName().contains("영어1") || subjectInfoObj.getName().contains("영어2") || subjectInfoObj.getName().contains("영어3") || subjectInfoObj.getName().contains("영어4") || subjectInfoObj.getName().contains("영어회화1") || subjectInfoObj.getName().contains("영어회화2") || subjectInfoObj.getName().contains("영어회화3") || subjectInfoObj.getName().contains("영어회화4") || subjectInfoObj.getName().contains("한국어1") || subjectInfoObj.getName().contains("한국어2") || subjectInfoObj.getName().contains("한국어3") || subjectInfoObj.getName().contains("한국어4") || subjectInfoObj.getName().contains("한국어연습1") || subjectInfoObj.getName().contains("한국어연습2") || subjectInfoObj.getName().contains("한국어연습3") || subjectInfoObj.getName().contains("한국어연습4") || subjectInfoObj.getName().contains("4차산업혁명과 미래사회 진로선택") || subjectInfoObj.getName().contains("디지털리터리시의 이해")) {
                            commonElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("공통에 추가");

                        }
                    }
                }

                    try {
                        if (currentPage < 338) {
                            if (currentPage % 10 != 0) {
                                WebElement pageLink = driver.findElement(By.xpath("//a[contains(@href, \"javascript:listPage('" + (currentPage * 10 + 1) + "')\")]"));
                                pageLink.click();
                            } else {
                                WebElement nextPageLink = driver.findElement(By.cssSelector("span.next a"));
                                nextPageLink.click();
                            }

                            currentPage++;
                            wait.until(ExpectedConditions.stalenessOf(elements.get(0)));
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.err.println("데이터 크롤링 중 오류 발생: " + e.getMessage());
                        driver.quit(); // 드라이버 종료
/*
                    e.printStackTrace(); // 스택 트레이스를 출력하여 문제를 진단
*/
                            }
                        }
                    } finally {
                        driver.quit(); // 드라이버 종료
                    }
                }



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
    public List<Subject> getMajors(){
        return new ArrayList<>(majorsMap.values());
    }


    @Override
    public List<Subject> getCoreElectives(){
        return new ArrayList<>(coreElectivesMap.values());
    }


    @Override
    public List<Subject> getCommonElectives(){
        return new ArrayList<>(commonElectivesMap.values());
    }

    @Override
    public List<Subject> getAllElectives(){
        List<Subject> allElectives = new ArrayList<>();
        allElectives.addAll(getMajors());
        allElectives.addAll(getCoreElectives());
        allElectives.addAll(getCommonElectives());

        return allElectives;
    }

}