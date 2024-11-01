package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Information;
import java.util.List;
import java.util.Optional;

public interface InformationRepository {
    void save(Information information); // 공모전 정보 저장
    Optional<Information> findByName(String name); // 이름으로 공모전 정보 찾기
    List<Information> findAll(); // 모든 공모전 정보 조회
    void update(String name, Information updatedInformation); // 공모전 정보 수정
    void delete(String name); // 공모전 정보 삭제
}
