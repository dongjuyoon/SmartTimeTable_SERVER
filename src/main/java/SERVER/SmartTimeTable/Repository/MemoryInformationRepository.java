package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Information;
import org.springframework.stereotype.Repository;

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
}
