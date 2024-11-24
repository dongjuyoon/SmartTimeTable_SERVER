package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Member;
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
import java.util.*;

@Repository
public class MemorySubjectRepository implements SubjectRepository {
    @Autowired
    private SubjectRepository subjectRepository;
    private final Map<String, Subject> subjectMap = new HashMap<>();
    private final Map<String, Subject> majorsMap = new HashMap<>();
    private final Map<String, Subject> coreElectivesMap = new HashMap<>();
    private final Map<String, Subject> commonElectivesMap = new HashMap<>();
    ////////////////////////////////////////////////////////////////////////
    private final Map<String, Subject> philosophyHumanityMap = new HashMap<>();
    private final Map<String, Subject> understandingModernContemporaryKoreanHistoryMap = new HashMap<>();
    private final Map<String, Subject> historyCivilizationMap = new HashMap<>();
    private final Map<String, Subject> criticalThinkingFor4thIndustrialRevolutionMap = new HashMap<>();
    private final Map<String, Subject> koreanCulturalHeritageThroughDigitalContentMap = new HashMap<>();
    private final Map<String, Subject> globalizationAndSocialChangeMap = new HashMap<>();
    private final Map<String, Subject> democracyAndModernSocietyMap = new HashMap<>();
    private final Map<String, Subject> introductionToEntrepreneurshipMap = new HashMap<>();
    private final Map<String, Subject> womenMinoritiesAndCommunityMap = new HashMap<>();
    private final Map<String, Subject> modernSocietyAndPsychologyMap = new HashMap<>();
    private final Map<String, Subject> jobPerformanceAndStrategicCommunicationMap = new HashMap<>();
    private final Map<String, Subject> globalCultureMap = new HashMap<>();
    private final Map<String, Subject> humanitiesThroughClassicsMap = new HashMap<>();
    private final Map<String, Subject> artAndCreativityMap = new HashMap<>();
    private final Map<String, Subject> artInThe4thIndustrialRevolutionEraMap = new HashMap<>();
    private final Map<String, Subject> culturalLiteracyAndCreativeStorytellingMap = new HashMap<>();
    private final Map<String, Subject> understandingDigitalCultureMap = new HashMap<>();
    private final Map<String, Subject> universeLifeAndMindMap = new HashMap<>();
    private final Map<String, Subject> environmentAndHumanityMap = new HashMap<>();
    private final Map<String, Subject> introductionToSWProgrammingMap = new HashMap<>();
    private final Map<String, Subject> worldOfArtificialIntelligenceMap = new HashMap<>();
    private final Map<String, Subject> understandingThe4thIndustrialRevolutionMap = new HashMap<>();
    private final Map<String, Subject> dataAnalysisAndAIUsingPythonMap = new HashMap<>();
    ///////////////////////////////////////////////////////////////////////////////
    private final Map<String, Subject> bibleAndHumanUnderstandingMap = new HashMap<>();
    private final Map<String, Subject> modernSocietyAndChristianEthicsMap = new HashMap<>();
    private final Map<String, Subject> religionAndScienceMap = new HashMap<>();
    private final Map<String, Subject> christianityAndCultureMap = new HashMap<>();
    private final Map<String, Subject> writingMap = new HashMap<>();
    private final Map<String, Subject> presentationAndDebateMap = new HashMap<>();
    private final Map<String, Subject> english1Map = new HashMap<>();
    private final Map<String, Subject> english2Map = new HashMap<>();
    private final Map<String, Subject> english3Map = new HashMap<>();
    private final Map<String, Subject> english4Map = new HashMap<>();
    private final Map<String, Subject> englishConversation1Map = new HashMap<>();
    private final Map<String, Subject> englishConversation2Map = new HashMap<>();
    private final Map<String, Subject> englishConversation3Map = new HashMap<>();
    private final Map<String, Subject> englishConversation4Map = new HashMap<>();
    private final Map<String, Subject> IndustrialRevolutionAndFutureSocietyCareerChoice4thMap = new HashMap<>();
    private final Map<String, Subject> understandingDigitalLiteracyNewCourseMap = new HashMap<>();
    ///////////////////////////////////////////////////////////////////////////////////////
    private final Map<String, Subject> CProgrammingMap = new HashMap<>();
    private final Map<String, Subject> introductionToEngineeringDesignMap = new HashMap<>();
    private final Map<String, Subject> objectOrientedProgramming1Map = new HashMap<>();
    private final Map<String, Subject> objectOrientedProgramming2Map = new HashMap<>();
    private final Map<String, Subject> dataStructuresMap = new HashMap<>();
    private final Map<String, Subject> computerHardwareMap = new HashMap<>();
    private final Map<String, Subject> webProgrammingMap = new HashMap<>();
    private final Map<String, Subject> advancedObjectOrientedProgrammingMap = new HashMap<>();
    private final Map<String, Subject> teamProject1Map = new HashMap<>();
    private final Map<String, Subject> openSWPracticeMap = new HashMap<>();
    private final Map<String, Subject> computerNetworksMap = new HashMap<>();
    private final Map<String, Subject> databasesMap = new HashMap<>();
    private final Map<String, Subject> operatingSystemsMap = new HashMap<>();
    private final Map<String, Subject> softwareEngineeringMap = new HashMap<>();
    private final Map<String, Subject> computerArchitectureMap = new HashMap<>();
    private final Map<String, Subject> teamProject2Map = new HashMap<>();
    private final Map<String, Subject> systemsAnalysisAndDesignMap = new HashMap<>();
    private final Map<String, Subject> algorithmsMap = new HashMap<>();
    private final Map<String, Subject> embeddedSystemsMap = new HashMap<>();
    private final Map<String, Subject> databaseDesignMap = new HashMap<>();
    private final Map<String, Subject> programmingLanguagesMap = new HashMap<>();
    private final Map<String, Subject> computerSecurityMap = new HashMap<>();
    private final Map<String, Subject> capstoneDesignMap = new HashMap<>();
    private final Map<String, Subject> computerGraphicsMap = new HashMap<>();
    private final Map<String, Subject> computerEngineeringSpecialTopics1Map = new HashMap<>();
    private final Map<String, Subject> systemCloudSecurityMap = new HashMap<>();
    private final Map<String, Subject> blockchainMap = new HashMap<>();
    private final Map<String, Subject> IndustrialRevolutionAndEntrepreneurship4thMap = new HashMap<>();
    private final Map<String, Subject> machineLearningMap = new HashMap<>();
    private final Map<String, Subject> computerEngineeringSpecialTopics2Map = new HashMap<>();
    private final Map<String, Subject> networkComputingMap = new HashMap<>();
    private final Map<String, Subject> artificialIntelligenceMap = new HashMap<>();
    private final Map<String, Subject> mobileProgrammingMap = new HashMap<>();
    private final Map<String, Subject> cloudComputingMap = new HashMap<>();
    private final Map<String, Subject> backendSoftwareDevelopmentMap = new HashMap<>();


    public List<Subject> findAllSubjectsClassified() {
        List<Subject> allSubjects = new ArrayList<>();

        allSubjects.addAll(philosophyHumanityMap.values());
        allSubjects.addAll(understandingModernContemporaryKoreanHistoryMap.values());
        allSubjects.addAll(historyCivilizationMap.values());
        allSubjects.addAll(criticalThinkingFor4thIndustrialRevolutionMap.values());
        allSubjects.addAll(koreanCulturalHeritageThroughDigitalContentMap.values());
        allSubjects.addAll(globalizationAndSocialChangeMap.values());
        allSubjects.addAll(democracyAndModernSocietyMap.values());
        allSubjects.addAll(introductionToEntrepreneurshipMap.values());
        allSubjects.addAll(womenMinoritiesAndCommunityMap.values());
        allSubjects.addAll(modernSocietyAndPsychologyMap.values());
        allSubjects.addAll(jobPerformanceAndStrategicCommunicationMap.values());
        allSubjects.addAll(globalCultureMap.values());
        allSubjects.addAll(humanitiesThroughClassicsMap.values());
        allSubjects.addAll(artAndCreativityMap.values());
        allSubjects.addAll(artInThe4thIndustrialRevolutionEraMap.values());
        allSubjects.addAll(culturalLiteracyAndCreativeStorytellingMap.values());
        allSubjects.addAll(understandingDigitalCultureMap.values());
        allSubjects.addAll(universeLifeAndMindMap.values());
        allSubjects.addAll(environmentAndHumanityMap.values());
        allSubjects.addAll(introductionToSWProgrammingMap.values());
        allSubjects.addAll(worldOfArtificialIntelligenceMap.values());
        allSubjects.addAll(understandingThe4thIndustrialRevolutionMap.values());
        allSubjects.addAll(dataAnalysisAndAIUsingPythonMap.values());

        // 공통 과목
        allSubjects.addAll(bibleAndHumanUnderstandingMap.values());
        allSubjects.addAll(modernSocietyAndChristianEthicsMap.values());
        allSubjects.addAll(religionAndScienceMap.values());
        allSubjects.addAll(christianityAndCultureMap.values());
        allSubjects.addAll(writingMap.values());
        allSubjects.addAll(presentationAndDebateMap.values());
        allSubjects.addAll(english1Map.values());
        allSubjects.addAll(english2Map.values());
        allSubjects.addAll(english3Map.values());
        allSubjects.addAll(english4Map.values());
        allSubjects.addAll(englishConversation1Map.values());
        allSubjects.addAll(englishConversation2Map.values());
        allSubjects.addAll(englishConversation3Map.values());
        allSubjects.addAll(englishConversation4Map.values());
        allSubjects.addAll(IndustrialRevolutionAndFutureSocietyCareerChoice4thMap.values());
        allSubjects.addAll(understandingDigitalLiteracyNewCourseMap.values());

        // 전공 과목
        allSubjects.addAll(CProgrammingMap.values());
        allSubjects.addAll(introductionToEngineeringDesignMap.values());
        allSubjects.addAll(objectOrientedProgramming1Map.values());
        allSubjects.addAll(objectOrientedProgramming2Map.values());
        allSubjects.addAll(dataStructuresMap.values());
        allSubjects.addAll(computerHardwareMap.values());
        allSubjects.addAll(webProgrammingMap.values());
        allSubjects.addAll(advancedObjectOrientedProgrammingMap.values());
        allSubjects.addAll(teamProject1Map.values());
        allSubjects.addAll(openSWPracticeMap.values());
        allSubjects.addAll(computerNetworksMap.values());
        allSubjects.addAll(databasesMap.values());
        allSubjects.addAll(operatingSystemsMap.values());
        allSubjects.addAll(softwareEngineeringMap.values());
        allSubjects.addAll(computerArchitectureMap.values());
        allSubjects.addAll(teamProject2Map.values());
        allSubjects.addAll(systemsAnalysisAndDesignMap.values());
        allSubjects.addAll(algorithmsMap.values());
        allSubjects.addAll(embeddedSystemsMap.values());
        allSubjects.addAll(databaseDesignMap.values());
        allSubjects.addAll(programmingLanguagesMap.values());
        allSubjects.addAll(computerSecurityMap.values());
        allSubjects.addAll(capstoneDesignMap.values());
        allSubjects.addAll(computerGraphicsMap.values());
        allSubjects.addAll(computerEngineeringSpecialTopics1Map.values());
        allSubjects.addAll(systemCloudSecurityMap.values());
        allSubjects.addAll(blockchainMap.values());
        allSubjects.addAll(IndustrialRevolutionAndEntrepreneurship4thMap.values());
        allSubjects.addAll(machineLearningMap.values());
        allSubjects.addAll(computerEngineeringSpecialTopics2Map.values());
        allSubjects.addAll(networkComputingMap.values());
        allSubjects.addAll(artificialIntelligenceMap.values());
        allSubjects.addAll(mobileProgrammingMap.values());
        allSubjects.addAll(cloudComputingMap.values());
        allSubjects.addAll(backendSoftwareDevelopmentMap.values());

        return allSubjects;
    }


    @Override
    public Subject findByName(String name) {
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
        System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
        //window용
/*
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\82108\\Downloads\\chromedriver-win64(130.0.6723.31)\\chromedriver-win64\\chromedriver.exe");
*/

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

                            if (subjectInfoObj.getName().contains("C언어프로그래밍")) {
                                CProgrammingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("C언어프로그래밍 입력");
                            } else if (subjectInfoObj.getName().contains("공학입문설계")) {
                                introductionToEngineeringDesignMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("공학입문설계 입력");
                            } else if (subjectInfoObj.getName().contains("객체지향프로그래밍1")) {
                                objectOrientedProgramming1Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("객체지향프로그래밍1 입력");
                            } else if (subjectInfoObj.getName().contains("객체지향프로그래밍2")) {
                                objectOrientedProgramming2Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("객체지향프로그래밍2 입력");
                            } else if (subjectInfoObj.getName().contains("자료구조")) {
                                dataStructuresMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("자료구조 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터하드웨어")) {
                                computerHardwareMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터하드웨어 입력");
                            } else if (subjectInfoObj.getName().contains("웹프로그래밍")) {
                                webProgrammingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("웹프로그래밍 입력");
                            } else if (subjectInfoObj.getName().contains("고급객체지향프로그래밍")) {
                                advancedObjectOrientedProgrammingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("고급객체지향프로그래밍 입력");
                            } else if (subjectInfoObj.getName().contains("팀프로젝트1")) {
                                teamProject1Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("팀프로젝트1 입력");
                            } else if (subjectInfoObj.getName().contains("공개SW실무")) {
                                openSWPracticeMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("공개SW실무 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터네트워크")) {
                                computerNetworksMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터네트워크 입력");
                            } else if (subjectInfoObj.getName().contains("데이터베이스")) {
                                databasesMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("데이터베이스 입력");
                            } else if (subjectInfoObj.getName().contains("운영체제")) {
                                operatingSystemsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("운영체제 입력");
                            } else if (subjectInfoObj.getName().contains("소프트웨어공학")) {
                                softwareEngineeringMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("소프트웨어공학 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터아키텍쳐")) {
                                computerArchitectureMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터아키텍쳐 입력");
                            } else if (subjectInfoObj.getName().contains("팀프로젝트2")) {
                                teamProject2Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("팀프로젝트2 입력");
                            } else if (subjectInfoObj.getName().contains("시스템분석및설계")) {
                                systemsAnalysisAndDesignMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("시스템분석및설계 입력");
                            } else if (subjectInfoObj.getName().contains("알고리즘")) {
                                algorithmsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("알고리즘 입력");
                            } else if (subjectInfoObj.getName().contains("임베디드시스템")) {
                                embeddedSystemsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("임베디드시스템 입력");
                            } else if (subjectInfoObj.getName().contains("데이터베이스설계")) {
                                databaseDesignMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("데이터베이스설계 입력");
                            } else if (subjectInfoObj.getName().contains("프로그래밍언어")) {
                                programmingLanguagesMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("프로그래밍언어 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터보안")) {
                                computerSecurityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터보안 입력");
                            } else if (subjectInfoObj.getName().contains("캡스톤디자인")) {
                                capstoneDesignMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("캡스톤디자인 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터그래픽스")) {
                                computerGraphicsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터그래픽스 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터공학특론1")) {
                                computerEngineeringSpecialTopics1Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터공학특론1 입력");
                            } else if (subjectInfoObj.getName().contains("시스템클라우드보안")) {
                                systemCloudSecurityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("시스템클라우드보안 입력");
                            } else if (subjectInfoObj.getName().contains("블록체인")) {
                                blockchainMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("블록체인 입력");
                            } else if (subjectInfoObj.getName().contains("4차산업혁명과기업가정신")) {
                                IndustrialRevolutionAndEntrepreneurship4thMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("4차산업혁명과기업가정신 입력");
                            } else if (subjectInfoObj.getName().contains("기계학습")) {
                                machineLearningMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("기계학습 입력");
                            } else if (subjectInfoObj.getName().contains("컴퓨터공학특론2")) {
                                computerEngineeringSpecialTopics2Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("컴퓨터공학특론2 입력");
                            } else if (subjectInfoObj.getName().contains("네트워크컴퓨팅")) {
                                networkComputingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("네트워크컴퓨팅 입력");
                            } else if (subjectInfoObj.getName().contains("인공지능")) {
                                artificialIntelligenceMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("인공지능 입력");
                            } else if (subjectInfoObj.getName().contains("모바일프로그래밍")) {
                                mobileProgrammingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("모바일프로그래밍 입력");
                            } else if (subjectInfoObj.getName().contains("클라우드컴퓨팅")) {
                                cloudComputingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("클라우드컴퓨팅 입력");
                            } else if (subjectInfoObj.getName().contains("백엔드소프트웨어개발")) {
                                backendSoftwareDevelopmentMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("백엔드소프트웨어개발 입력");
                            }


                        } else if (subjectInfoObj.getName().contains("철학과 인간") || subjectInfoObj.getName().contains("한국근현대사의 이해") || subjectInfoObj.getName().contains("역사와 문명") || subjectInfoObj.getName().contains("4차산업혁명을위한비판적사고") || subjectInfoObj.getName().contains("디지털콘텐츠로 만나는 한국의 문화유산") || subjectInfoObj.getName().contains("세계화와 사회변화") || subjectInfoObj.getName().contains("민주주의와 현대사회") || subjectInfoObj.getName().contains("창업입문") || subjectInfoObj.getName().contains("여성·소수자·공동체") || subjectInfoObj.getName().contains("현대사회와 심리학") || subjectInfoObj.getName().contains("직무수행과 전략적 의사소통") || subjectInfoObj.getName().contains("글로벌문화") || subjectInfoObj.getName().contains("고전으로읽는 인문학") || subjectInfoObj.getName().contains("예술과창조성") || subjectInfoObj.getName().contains("4차산업혁명시대의예술") || subjectInfoObj.getName().contains("문화리터러시와창의적스토리텔링") || subjectInfoObj.getName().contains("디지털문화의 이해") || subjectInfoObj.getName().contains("환경과 인간") || subjectInfoObj.getName().contains("우주,생명,마음") || subjectInfoObj.getName().contains("SW프로그래밍입문") || subjectInfoObj.getName().contains("인공지능의 세계") || subjectInfoObj.getName().contains("4차산업혁명의 이해") || subjectInfoObj.getName().contains("파이썬을활용한데이터분석과인공지능") || subjectInfoObj.getName().contains("외국인학생을위한컴퓨터활용")) {
                            coreElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("핵심에 추가");
                            if (subjectInfoObj.getName().contains("철학과 인간")) {
                                philosophyHumanityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("철학과 인간 입력");
                            } else if (subjectInfoObj.getName().contains("한국근현대사의 이해")) {
                                understandingModernContemporaryKoreanHistoryMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("한국근현대사의 이해 입력");
                            } else if (subjectInfoObj.getName().contains("역사와 문명")) {
                                historyCivilizationMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("역사와 문명 입력");
                            } else if (subjectInfoObj.getName().contains("4차산업혁명을위한비판적사고")) {
                                criticalThinkingFor4thIndustrialRevolutionMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("4차산업혁명을위한비판적사고 입력");
                            } else if (subjectInfoObj.getName().contains("디지털콘텐츠로 만나는 한국의 문화유산")) {
                                koreanCulturalHeritageThroughDigitalContentMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("디지털콘텐츠로 만나는 한국의 문화유산 입력");
                            } else if (subjectInfoObj.getName().contains("세계화와 사회변화")) {
                                globalizationAndSocialChangeMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("세계화와 사회변화 입력");
                            } else if (subjectInfoObj.getName().contains("민주주의와 현대사회")) {
                                democracyAndModernSocietyMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("민주주의와 현대사회 입력");
                            } else if (subjectInfoObj.getName().contains("창업입문")) {
                                introductionToEntrepreneurshipMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("창업입문 입력");
                            } else if (subjectInfoObj.getName().contains("여성·소수자·공동체")) {
                                womenMinoritiesAndCommunityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("여성·소수자·공동체 입력");
                            } else if (subjectInfoObj.getName().contains("현대사회와 심리학")) {
                                modernSocietyAndPsychologyMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("현대사회와 심리학 입력");
                            } else if (subjectInfoObj.getName().contains("직무수행과 전략적 의사소통")) {
                                jobPerformanceAndStrategicCommunicationMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("직무수행과 전략적 의사소통 입력");
                            } else if (subjectInfoObj.getName().contains("글로벌문화")) {
                                globalCultureMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("글로벌문화 입력");
                            } else if (subjectInfoObj.getName().contains("고전으로읽는 인문학")) {
                                humanitiesThroughClassicsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("고전으로읽는 인문학 입력");
                            } else if (subjectInfoObj.getName().contains("예술과창조성")) {
                                artAndCreativityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("예술과창조성 입력");
                            } else if (subjectInfoObj.getName().contains("4차산업혁명시대의예술")) {
                                artInThe4thIndustrialRevolutionEraMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("4차산업혁명시대의예술 입력");
                            } else if (subjectInfoObj.getName().contains("문화리터러시와창의적스토리텔링")) {
                                culturalLiteracyAndCreativeStorytellingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("문화리터러시와창의적스토리텔링 입력");
                            } else if (subjectInfoObj.getName().contains("디지털문화의 이해")) {
                                understandingDigitalCultureMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("디지털문화의 이해 입력");
                            } else if (subjectInfoObj.getName().contains("우주,생명,마음")) {
                                universeLifeAndMindMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("우주,생명,마음 입력");
                            } else if (subjectInfoObj.getName().contains("환경과 인간")) {
                                environmentAndHumanityMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("환경과 인간 입력");
                            } else if (subjectInfoObj.getName().contains("SW프로그래밍입문")) {
                                introductionToSWProgrammingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("SW프로그래밍입문 입력");
                            } else if (subjectInfoObj.getName().contains("인공지능의 세계")) {
                                worldOfArtificialIntelligenceMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("인공지능의 세계 입력");
                            } else if (subjectInfoObj.getName().contains("4차산업혁명의 이해")) {
                                understandingThe4thIndustrialRevolutionMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("4차산업혁명의 이해 입력");
                            } else if (subjectInfoObj.getName().contains("파이썬을활용한데이터분석과인공지능")) {
                                dataAnalysisAndAIUsingPythonMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("파이썬을활용한데이터분석과인공지능 입력");
                            }

                        } else if (subjectInfoObj.getName().contains("채플") || subjectInfoObj.getName().contains("성서와인간이해") || subjectInfoObj.getName().contains("현대사회와 기독교 윤리") || subjectInfoObj.getName().contains("종교와과학") || subjectInfoObj.getName().contains("기독교와문화") || subjectInfoObj.getName().contains("글쓰기") || subjectInfoObj.getName().contains("발표와토의") || subjectInfoObj.getName().contains("영어1") || subjectInfoObj.getName().contains("영어2") || subjectInfoObj.getName().contains("영어3") || subjectInfoObj.getName().contains("영어4") || subjectInfoObj.getName().contains("영어회화1") || subjectInfoObj.getName().contains("영어회화2") || subjectInfoObj.getName().contains("영어회화3") || subjectInfoObj.getName().contains("영어회화4") || subjectInfoObj.getName().contains("4차산업혁명과 미래사회 진로선택") || subjectInfoObj.getName().contains("디지털리터리시의 이해")) {
                            commonElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("공통에 추가");
                            if (subjectInfoObj.getName().contains("성서와인간이해")) {
                                bibleAndHumanUnderstandingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("성서와 인간 이해 입력");
                            } else if (subjectInfoObj.getName().contains("현대사회와 기독교 윤리")) {
                                modernSocietyAndChristianEthicsMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("현대사회와 기독교 윤리 입력");
                            } else if (subjectInfoObj.getName().contains("종교와과학")) {
                                religionAndScienceMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("종교와 과학 입력");
                            } else if (subjectInfoObj.getName().contains("기독교와문화")) {
                                christianityAndCultureMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("기독교와 문화 입력");
                            } else if (subjectInfoObj.getName().contains("글쓰기")) {
                                writingMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("글쓰기 입력");
                            } else if (subjectInfoObj.getName().contains("발표와토의")) {
                                presentationAndDebateMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("발표와 토의 입력");
                            } else if (subjectInfoObj.getName().contains("영어1")) {
                                english1Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어1 입력");
                            } else if (subjectInfoObj.getName().contains("영어2")) {
                                english2Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어2 입력");
                            } else if (subjectInfoObj.getName().contains("영어3")) {
                                english3Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어3 입력");
                            } else if (subjectInfoObj.getName().contains("영어4")) {
                                english4Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어4 입력");
                            } else if (subjectInfoObj.getName().contains("영어회화1")) {
                                englishConversation1Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어회화1 입력");
                            } else if (subjectInfoObj.getName().contains("영어회화2")) {
                                englishConversation2Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어회화2 입력");
                            } else if (subjectInfoObj.getName().contains("영어회화3")) {
                                englishConversation3Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어회화3 입력");
                            } else if (subjectInfoObj.getName().contains("영어회화4")) {
                                englishConversation4Map.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("영어회화4 입력");
                            } else if (subjectInfoObj.getName().contains("4차산업혁명과 미래사회 진로선택")) {
                                IndustrialRevolutionAndFutureSocietyCareerChoice4thMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("4차산업혁명과 미래사회 진로선택 입력");
                            } else if (subjectInfoObj.getName().contains("디지털리터리시의 이해")) {
                                understandingDigitalLiteracyNewCourseMap.put(subjectInfoObj.getName(), subjectInfoObj);
                                System.out.println("디지털 리터러시의 이해 입력");
                            }

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
                    } else{
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

    public void addSubject(Subject subject) {
        subjectMap.put(subject.getName(), subject);
    }

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
    // 과목의 선이수 조건을 정의하고 반환하는 메서드
    @Override
    public Map<String, List<String>> getCoursePrerequisites() {
        return new HashMap<>() {{
            put("자료구조", Collections.singletonList("C언어프로그래밍"));
            put("객체지향프로그래밍1", Collections.singletonList("C언어프로그래밍"));
            put("객체지향프로그래밍2", Collections.singletonList("객체지향프로그래밍1"));
            put("알고리즘", Collections.singletonList("자료구조"));
            put("고급객체지향프로그래밍", Collections.singletonList("객체지향프로그래밍2"));
            put("팀프로젝트", Collections.singletonList("공학입문설계"));
            put("컴퓨터하드웨어", Collections.singletonList("공학입문설계"));
            put("시스템분석 및 설계", Collections.singletonList("공학입문설계"));
            put("임베디드시스템", Arrays.asList("컴퓨터하드웨어", "공개SW실무"));
            put("캡스톤디자인", Collections.singletonList("소프트웨어공학"));
            put("컴퓨터아키텍처", Collections.singletonList("컴퓨터하드웨어"));
            put("컴퓨터네트워크", Collections.singletonList("컴퓨터하드웨어"));
            put("데이터베이스설계", Collections.singletonList("데이터베이스"));
        }};
    }

    // 선이수 과목을 추천하는 메서드
    @Override
    public void recommendPrerequisites(List<String> enrolledMajorElectives,
                                        Map<String, List<Subject>> recommendedSubjectsMap,
                                        List<String> prerequisites) {
        for (String prerequisite : prerequisites) {
            if (!enrolledMajorElectives.contains(prerequisite)) {
                addRecommendedSubject(prerequisite, recommendedSubjectsMap);
            }
        }
    }

    // 학년 및 학기에 따른 과목 추천 메서드
    @Override
  public  void recommendSubjectsByGradeAndSemester(String grade, String semester, String subject,
                                                     List<String> enrolledMajorElectives,
                                                     Map<String, List<Subject>> recommendedSubjectsMap) {
        switch (grade) {
            case "1":
                if (semester.equals("1")) {
                    if (subject.equals("C언어프로그래밍") || subject.equals("공학입문설계")) {
                        addRecommendedSubject(subject, recommendedSubjectsMap);
                    }
                } else if (semester.equals("2") && subject.equals("객체지향프로그래밍1")
                        && enrolledMajorElectives.contains("C언어프로그래밍")) {
                    addRecommendedSubject(subject, recommendedSubjectsMap);
                }
                break;
            case "2":
                if (semester.equals("1")) {
                    if (subject.equals("자료구조") || subject.equals("객체지향프로그래밍2")
                            || (subject.equals("컴퓨터하드웨어") && enrolledMajorElectives.contains("공학입문설계"))) {
                        addRecommendedSubject(subject, recommendedSubjectsMap);
                    }
                } else if (semester.equals("2") && subject.equals("팀프로젝트")
                        && enrolledMajorElectives.contains("공학입문설계")) {
                    addRecommendedSubject(subject, recommendedSubjectsMap);
                }
                break;
            case "3":
                if (semester.equals("1")) {
                    if (subject.equals("데이터베이스") || subject.equals("소프트웨어공학") || subject.equals("운영체제")
                            || (subject.equals("컴퓨터네트워크") && enrolledMajorElectives.contains("컴퓨터하드웨어"))
                            || (subject.equals("컴퓨터아키텍처") && enrolledMajorElectives.contains("컴퓨터하드웨어"))) {
                        addRecommendedSubject(subject, recommendedSubjectsMap);
                    }
                } else if (semester.equals("2") && (subject.equals("알고리즘") && enrolledMajorElectives.contains("자료구조"))
                        || (subject.equals("데이터베이스설계") && enrolledMajorElectives.contains("데이터베이스"))) {
                    addRecommendedSubject(subject, recommendedSubjectsMap);
                }
                break;
            case "4":
                if (semester.equals("1")) {
                    if (subject.equals("캡스톤 디자인") && !enrolledMajorElectives.contains("소프트웨어공학")) {
                        addRecommendedSubject(subject, recommendedSubjectsMap);
                    }
                } else if (semester.equals("2")) {
                    addRecommendedSubject(subject, recommendedSubjectsMap);
                }
                break;
        }
    }

    // 추천 과목을 추가하는 메서드
    @Override
    public void addRecommendedSubject(String subject, Map<String, List<Subject>> recommendedSubjectsMap) {

        Subject subjectEntity = subjectRepository.findByName(subject);

        if (subjectEntity == null) {
            System.out.println("과목이 존재하지 않습니다");
            return;
        }
        recommendedSubjectsMap.computeIfAbsent("추천 과목", k -> new ArrayList<>()).add(subjectEntity);
    }
    public Map<String, List<Subject>> findRecommendedCoreSubjects(Member member) {
        // 수강 중인 과목 리스트
        List<String> enrolledCoreElectives = member.getCoreElectives();

        // 추천 과목 목록
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("철학과 인간", "한국근현대사의 이해", "역사와 문명", "4차산업혁명을위한비판적사고", "디지털 콘텐츠로 만나는 한국의 문화유산"),
                List.of("세계화와 사회변화", "민주주의와 현대사회", "창업입문", "여성·소수자·공동체", "현대사회와 심리학", "직무수행과 전략적 의사소통"),
                List.of("고전으로읽는 인문학", "예술과창조성", "4차산업혁명시대의예술", "문화리터러시와창의적스토리텔링", "디지털문화의 이해"),
                List.of("환경과 인간", "우주, 생명, 마음", "SW프로그래밍입문", "인공지능의 세계", "4차산업혁명의 이해", "파이썬을 활용한 데이터 분석과 인공지능")
        );

        // 추천 과목 맵 초기화
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();

        // 추천 과목 리스트 초기화
        for (List<String> subjectList : allRecommendedSubjects) {
            // 해당 그룹에 수강 중인 과목이 포함되어 있으면 전체 그룹 제외
            if (enrolledCoreElectives.stream().anyMatch(subjectList::contains)) {
                continue; // 이 그룹의 과목은 추천하지 않음
            }

            // 해당 그룹의 추천 과목을 찾기
            List<Subject> subjects = new ArrayList<>();
            for (String subjectName : subjectList) {
                Subject subject = subjectRepository.findByName(subjectName);
                if (subject != null) {
                    subjects.add(subject);
                }
            }

            // 추천 과목이 있는 경우 추가
            if (!subjects.isEmpty()) {
                recommendedSubjectsMap.put(subjectList.toString(), subjects);
            }
        }

        return recommendedSubjectsMap;
    }
    public List<String> findRecommendedCommonSubjects(Member member) {
        List<String> enrolledCommonElectives = member.getCommonElectives();

        // 추천 과목군들
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("성서와인간이해", "현대사회와 기독교 윤리", "종교와과학", "기독교와문화"),
                List.of("글쓰기", "발표와토의"),
                List.of("영어1", "영어2", "영어3", "영어4"),
                List.of("영어회화1", "영어회화2", "영어회화3", "영어회화4"),
                List.of("4차산업혁명과 미래사회 진로선택", "디지털리터리시의 이해")
        );

        List<String> recommendedSubjects = new ArrayList<>();

        // 1. 첫 번째 그룹 처리
        List<String> subjectGroup1 = allRecommendedSubjects.get(0);
        long subjectGroup1Count = enrolledCommonElectives.stream().filter(subjectGroup1::contains).count();
        if (subjectGroup1Count == 0) {
            recommendedSubjects.addAll(subjectGroup1); // 아무것도 듣지 않으면 모두 추천
        } else if (subjectGroup1Count == 1) {
            recommendedSubjects.addAll(subjectGroup1.stream()
                    .filter(subject -> !enrolledCommonElectives.contains(subject))
                    .toList());
        }

        // 2. 두 번째 그룹 처리
        List<String> subjectGroup2 = allRecommendedSubjects.get(1);
        if (!enrolledCommonElectives.stream().anyMatch(subjectGroup2::contains)) {
            recommendedSubjects.addAll(subjectGroup2); // 둘 다 안 들으면 추천
        }

        // 3. 영어 과목 추천 처리
        boolean hasEnglish1 = enrolledCommonElectives.contains("영어1");
        boolean hasEnglish2 = enrolledCommonElectives.contains("영어2");
        boolean hasEnglish3 = enrolledCommonElectives.contains("영어3");
        boolean hasEnglish4 = enrolledCommonElectives.contains("영어4");

        if (!hasEnglish1 && !hasEnglish2 && !hasEnglish3 && !hasEnglish4) {
            recommendedSubjects.add("영어1");
            recommendedSubjects.add("영어3");
        } else if (hasEnglish1) {
            recommendedSubjects.add("영어2");
        } else if (hasEnglish3) {
            recommendedSubjects.add("영어4");
        }

        // 4. 영어 회화 과목 추천 처리
        boolean hasEnglishTalk1 = enrolledCommonElectives.contains("영어회화1");
        boolean hasEnglishTalk2 = enrolledCommonElectives.contains("영어회화2");
        boolean hasEnglishTalk3 = enrolledCommonElectives.contains("영어회화3");
        boolean hasEnglishTalk4 = enrolledCommonElectives.contains("영어회화4");

        if (!hasEnglishTalk1 && !hasEnglishTalk2 && !hasEnglishTalk3 && !hasEnglishTalk4) {
            recommendedSubjects.add("영어회화1");
            recommendedSubjects.add("영어회화3");
        } else if (hasEnglishTalk1) {
            recommendedSubjects.add("영어회화2");
        } else if (hasEnglishTalk3) {
            recommendedSubjects.add("영어회화4");
        }

        // 5. 마지막 그룹 처리
        List<String> subjectGroup7 = allRecommendedSubjects.get(4);
        if (!enrolledCommonElectives.stream().anyMatch(subjectGroup7::contains)) {
            recommendedSubjects.addAll(subjectGroup7);
        }

        return recommendedSubjects;
    }





}