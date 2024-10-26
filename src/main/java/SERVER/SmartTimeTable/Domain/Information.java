package SERVER.SmartTimeTable.Domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Information {
    private String name;//관심 분야
    private String applicationPeriod; //응모기간
    private String homepageLink; //홈페이지 주소
    private String photoLink; //사진 주소
}
