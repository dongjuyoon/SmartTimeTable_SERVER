package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Information;
import java.util.List;

public interface InformationRepository {
    void save(Information information); // 공모전 정보 저장
    Information findByName(String name); // ID로 공모전 정보 찾기
    List<Information> findAll(); // 모든 공모전 정보 조회
    void update(String id, Information updatedInformation); // 공모전 정보 수정
    void delete(String id); // 공모전 정보 삭제
}
