package SERVER.SmartTimeTable.Domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Information {
    private String name; // 공모전 이름
    private String applicationPeriod; // 응모기간
    private String homepageLink; // 홈페이지 주소
    private String photoLink; // 사진 주소

    public Information(String name, String homepageLink, String applicationPeriod, String photoLink) {
        this.name = name;
        this.homepageLink = homepageLink;
        this.applicationPeriod = applicationPeriod;
        this.photoLink = photoLink;
    }
}
