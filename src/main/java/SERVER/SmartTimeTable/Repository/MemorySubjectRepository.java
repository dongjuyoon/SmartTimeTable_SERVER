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
import org.springframework.stereotype.Repository;


import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemorySubjectRepository implements SubjectRepository {

    private final Map<String, Subject> subjectMap = new HashMap<>();
    private final Map<String, Subject> subjectMap2 = new HashMap<>();
    private final Map<String, Subject> subjectMap3 = new HashMap<>();
    private final Map<String, Subject> majorsMap = new HashMap<>();
    private final Map<String, Subject> coreElectivesMap = new HashMap<>();
    private final Map<String, Subject> commonElectivesMap = new HashMap<>();
    private List<String> allMajors = new ArrayList<>();



    //괄호 제거 이름으로 찾기
    @Override
    public List<Subject> findByName(String name) {
        System.out.println("찾고 있는 과목 이름: " + name);

        // 현재 과목 목록 출력
        System.out.println("현재 과목 목록: " + subjectMap3.keySet());

        List<Subject> foundSubjects = new ArrayList<>(); // 찾은 과목 리스트

        // 키를 검색하여 name이 포함된 과목 찾기
        for (String key : subjectMap3.keySet()) {
            if (key.contains(name)) {
                Subject foundSubject = subjectMap3.get(key);
                if (foundSubject != null) {
                    foundSubjects.add(foundSubject); // 찾은 과목을 리스트에 추가
                    System.out.println("찾은 과목: " + foundSubject.getName() + " (키: " + key + ")");
                }
            }
        }

        if (foundSubjects.isEmpty()) {
            System.out.println("과목을 찾지 못했습니다: " + name);
        }

        return foundSubjects; // 찾은 과목 리스트 반환
    }


    //일반 이름으로 찾기
    @Override
    public Subject findByName2(String name){
        return subjectMap3.get(name);
    }



    @Override
    public void save(Subject subject) {
        subjectMap.put(subject.getLectureNumber(), subject); // 강좌번호를 키로 사용

        String normalizedName = normalizeName(subject.getName());
        String key = normalizedName;
        int index = 1;

        // 같은 키가 이미 존재하는 경우 인덱스를 추가하여 새로운 키 생성
        while (subjectMap2.containsKey(key)) {
            key = normalizedName + "_" + index; // 예: "과목명_1", "과목명_2" 형태로 키 생성
            index++;
        }

        subjectMap2.put(key, subject); // 괄호제거된 새로운 키로 과목 저장

        subjectMap3.put(subject.getName(), subject); //일반 이름을 키로 사용
    }

    private String normalizeName(String name) {
        return name.replaceAll("\\s*\\(.*?\\)", "").trim(); // 괄호 및 그 안의 내용 제거
    }

    @PostConstruct
    public void fetchCourseData() {
        //os용
        System.setProperty("webdriver.chrome.driver", "/Users/holang/Downloads/chromedriver-mac-x64/chromedriver");
        /*//window용

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

                        } else if (subjectInfoObj.getName().contains("철학과인간") || subjectInfoObj.getName().contains("한국근현대사의이해") || subjectInfoObj.getName().contains("역사와문명") || subjectInfoObj.getName().contains("4차산업혁명을위한비판적사고") || subjectInfoObj.getName().contains("디지털콘텐츠로만나는한국의문화유산") || subjectInfoObj.getName().contains("세계화와사회변화") || subjectInfoObj.getName().contains("민주주의와현대사회") || subjectInfoObj.getName().contains("창업입문") || subjectInfoObj.getName().contains("여성·소수자·공동체") || subjectInfoObj.getName().contains("현대사회와심리학") || subjectInfoObj.getName().contains("직무수행과전략적의사소통") || subjectInfoObj.getName().contains("글로벌문화") || subjectInfoObj.getName().contains("고전으로읽는인문학") || subjectInfoObj.getName().contains("예술과창조성") || subjectInfoObj.getName().contains("4차산업혁명시대의예술") || subjectInfoObj.getName().contains("문화리터러시와창의적스토리텔링") || subjectInfoObj.getName().contains("디지털문화의이해") || subjectInfoObj.getName().contains("환경과인간") || subjectInfoObj.getName().contains("우주,생명,마음") || subjectInfoObj.getName().contains("SW프로그래밍입문") || subjectInfoObj.getName().contains("인공지능의세계") || subjectInfoObj.getName().contains("4차산업혁명의이해") || subjectInfoObj.getName().contains("파이썬을활용한데이터분석과인공지능") || subjectInfoObj.getName().contains("외국인학생을위한컴퓨터활용")) {
                            coreElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("핵심에 추가");
                        } else if (subjectInfoObj.getName().contains("채플") || subjectInfoObj.getName().contains("성서와인간이해") || subjectInfoObj.getName().contains("현대사회와기독교윤리") || subjectInfoObj.getName().contains("종교와과학") || subjectInfoObj.getName().contains("기독교와문화") || subjectInfoObj.getName().contains("글쓰기") || subjectInfoObj.getName().contains("발표와토의") || subjectInfoObj.getName().contains("영어1") || subjectInfoObj.getName().contains("영어2") || subjectInfoObj.getName().contains("영어3") || subjectInfoObj.getName().contains("영어4") || subjectInfoObj.getName().contains("영어회화1") || subjectInfoObj.getName().contains("영어회화2") || subjectInfoObj.getName().contains("영어회화3") || subjectInfoObj.getName().contains("영어회화4") || subjectInfoObj.getName().contains("4차산업혁명과미래사회진로선택") || subjectInfoObj.getName().contains("디지털리터리시의이해")) {
                            commonElectivesMap.put(subjectInfoObj.getLectureNumber(), subjectInfoObj);
                            System.out.println("공통에 추가");
                        }
                        allMajors.add("C언어프로그래밍");
                        allMajors.add("공학입문설계");
                        allMajors.add("객체지향프로그래밍1");
                        allMajors.add("자료구조");
                        allMajors.add("객체지향프로그래밍2");
                        allMajors.add("컴퓨터하드웨어");
                        allMajors.add("팀프로젝트1");
                        allMajors.add("웹프로그래밍");
                        allMajors.add("공개SW실무");
                        allMajors.add("고급객체지향프로그래밍");
                        allMajors.add("데이터베이스");
                        allMajors.add("소프트웨어공학");
                        allMajors.add("운영체제");
                        allMajors.add("컴퓨터네트워크");
                        allMajors.add("컴퓨터아키텍처");
                        allMajors.add("시스템분석 및 설계");
                        allMajors.add("팀프로젝트2");
                        allMajors.add("알고리즘");
                        allMajors.add("데이터베이스설계");
                        allMajors.add("시스템프로그래밍");
                        allMajors.add("프로그래밍언어");
                        allMajors.add("임베디드시스템");
                        allMajors.add("컴퓨터 보안");
                        allMajors.add("캡스톤 디자인");
                        allMajors.add("블록체인");
                        allMajors.add("컴퓨터그래픽스");
                        allMajors.add("시스템클라우드보안");
                        allMajors.add("기계학습");
                        allMajors.add("인공지능");
                        allMajors.add("모바일프로그래밍");
                        allMajors.add("백엔드소프트웨어개발");
                        allMajors.add("클라우드컴퓨팅");
                        allMajors.add("현장실습");

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

                    e.printStackTrace(); // 스택 트레이스를 출력하여 문제를 진단

                }
            }
        } finally {
            driver.quit(); // 드라이버 종료
        }
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
    public List<String> getAllMajors(){
        return allMajors;
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
            put("팀프로젝트1", Collections.singletonList("공학입문설계"));
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
            if (!enrolledMajorElectives.contains(prerequisite) &&
                    !recommendedSubjectsMap.getOrDefault("추천 과목", new ArrayList<>()).contains(prerequisite)) {
                addRecommendedSubject(prerequisite, recommendedSubjectsMap);
            }
        }
    }

    // 학년 및 학기에 따른 과목 추천 메서드
    @Override
    public void recommendSubjectsByGradeAndSemester(String grade, String semester, String subject,
                                                    List<String> enrolledMajorElectives,
                                                    Map<String, List<Subject>> recommendedSubjectsMap) {

        if (isSubjectRecommendedForSemester(subject, grade, semester, enrolledMajorElectives)) {
            addRecommendedSubject(subject, recommendedSubjectsMap);
        }
    }

    // 학년 및 학기에 따른 추천 과목 조건 체크 메서드
    private boolean isSubjectRecommendedForSemester(String subject, String grade, String semester, List<String> enrolledMajorElectives) {
        switch (grade) {
            case "1":
                if (semester.equals("1")) {
                    return subject.equals("C언어프로그래밍") || subject.equals("공학입문설계");
                } else if (semester.equals("2")) {
                    return subject.equals("객체지향프로그래밍1") && enrolledMajorElectives.contains("C언어프로그래밍");
                }
                break;
            case "2":
                if (semester.equals("1")) {
                    return (subject.equals("자료구조") || subject.equals("객체지향프로그래밍2")) && enrolledMajorElectives.contains("C언어프로그래밍") ||
                            (subject.equals("컴퓨터하드웨어") && enrolledMajorElectives.contains("공학입문설계"));
                } else if (semester.equals("2")) {
                    return subject.equals("팀프로젝트1") && enrolledMajorElectives.contains("공학입문설계") ||
                            subject.equals("웹프로그래밍") ||
                            subject.equals("공개SW실무") ||
                            subject.equals("고급객체지향프로그래밍");
                }
                break;
            case "3":
                if (semester.equals("1")) {
                    return subject.equals("데이터베이스") ||
                            subject.equals("소프트웨어공학") ||
                            subject.equals("운영체제") ||
                            (subject.equals("컴퓨터네트워크") && enrolledMajorElectives.contains("컴퓨터하드웨어")) ||
                            (subject.equals("컴퓨터아키텍처") && enrolledMajorElectives.contains("컴퓨터하드웨어")) ||
                            subject.equals("시스템분석 및 설계") ||
                            subject.equals("팀프로젝트2");
                } else if (semester.equals("2")) {
                    return (subject.equals("알고리즘") && enrolledMajorElectives.contains("자료구조")) ||
                            (subject.equals("데이터베이스설계") && enrolledMajorElectives.contains("데이터베이스")) ||
                            subject.equals("시스템프로그래밍") ||
                            subject.equals("프로그래밍언어") ||
                            subject.equals("임베디드시스템") ||
                            subject.equals("컴퓨터 보안");
                }
                break;
            case "4":
                if (semester.equals("1")) {
                    return (subject.equals("캡스톤 디자인") && !enrolledMajorElectives.contains("소프트웨어공학")) ||
                            subject.equals("블록체인") ||
                            subject.equals("컴퓨터그래픽스") ||
                            subject.equals("시스템클라우드보안") ||
                            subject.equals("기계학습");
                } else if (semester.equals("2")) {
                    return subject.equals("인공지능") ||
                            subject.equals("모바일프로그래밍") ||
                            subject.equals("백엔드소프트웨어개발") ||
                            subject.equals("클라우드컴퓨팅") ||
                            subject.equals("현장실습");
                }
                break;
        }
        return false;
    }

    // 추천 과목을 추가하는 메서드
    public void addRecommendedSubject(String subjectName, Map<String, List<Subject>> recommendedSubjectsMap) {
        List<Subject> subjectEntities = findByName(subjectName); // subjectMap에서 과목 리스트 찾기

        if (subjectEntities.isEmpty()) {
            System.out.println("과목이 존재하지 않습니다: " + subjectName);
            return;
        }

        // 추천 과목에 추가
        recommendedSubjectsMap.computeIfAbsent("추천 과목", k -> new ArrayList<>()).addAll(subjectEntities);
    }
    

    public Map<String, List<Subject>> findRecommendedCoreSubjects(Member member) {
        // 수강 중인 과목 리스트
        List<String> enrolledCoreElectives = member.getCoreElectives();

        // 추천 과목 목록
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("철학과인간", "한국근현대사의이해", "역사와문명", "4차산업혁명을위한비판적사고", "디지털콘텐츠로만나는한국의문화유산"),
                List.of("세계화와사회변화", "민주주의와현대사회", "창업입문", "여성·소수자·공동체", "현대사회와심리학", "직무수행과전략적의사소통"),
                List.of("고전으로읽는인문학", "예술과창조성", "4차산업혁명시대의예술", "문화리터러시와창의적스토리텔링", "디지털문화의이해"),
                List.of("환경과인간", "우주,생명,마음", "SW프로그래밍입문", "인공지능의세계", "4차산업혁명의이해", "파이썬을활용한데이터분석과인공지능")
        );

        // 추천 과목 맵 초기화
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();

        // 추천 과목 그룹 순회
        for (List<String> subjectList : allRecommendedSubjects) {
            // 해당 그룹에 수강 중인 과목이 포함되어 있는지 체크
            boolean hasEnrolledSubject = enrolledCoreElectives.stream().anyMatch(subjectList::contains);

            // 수강 중인 과목이 없을 경우 전체 그룹 추천
            if (!hasEnrolledSubject) {
                List<Subject> foundSubjects = subjectList.stream()
                        .flatMap(subjectName -> findByName(subjectName).stream()) // 과목 이름으로 Subject 객체 찾기
                        .filter(Objects::nonNull) // null이 아닌 과목만 필터링
                        .toList(); // Java 16 이상에서 사용 가능

                // 추천 과목 그룹 추가
                recommendedSubjectsMap.put("추천 과목: " + subjectList.get(0), foundSubjects);
            }
        }

        return recommendedSubjectsMap; // 최종 추천 과목 맵 반환
    }


    public Map<String, List<Subject>> findRecommendedCommonSubjects(Member member) {
        List<String> enrolledCommonElectives = member.getCommonElectives();
        Map<String, List<Subject>> recommendedSubjectsMap = new HashMap<>();
        List<String> recommendedSubjects = new ArrayList<>();

        // 추천 과목군들
        List<List<String>> allRecommendedSubjects = List.of(
                List.of("성서와인간이해", "현대사회와기독교윤리", "종교와과학", "기독교와문화"),
                List.of("글쓰기", "발표와토의"),
                List.of("영어1", "영어2", "영어3", "영어4"),
                List.of("영어회화1", "영어회화2", "영어회화3", "영어회화4"),
                List.of("4차산업혁명과미래사회진로선택", "디지털리터리시의이해")
        );

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
        if (enrolledCommonElectives.stream().noneMatch(subjectGroup2::contains)) {
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
        if (enrolledCommonElectives.stream().noneMatch(subjectGroup7::contains)) {
            recommendedSubjects.addAll(subjectGroup7);
        }

        // Subject 객체로 변환
        for (String subjectName : recommendedSubjects) {
            List<Subject> subject = findByName(subjectName);
            if (subject != null) {
                recommendedSubjectsMap.computeIfAbsent("추천 과목", k -> new ArrayList<>()).addAll(subject);
            }
        }

        return recommendedSubjectsMap;
    }
}