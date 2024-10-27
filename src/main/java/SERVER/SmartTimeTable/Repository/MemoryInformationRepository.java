package SERVER.SmartTimeTable.Repository;

import SERVER.SmartTimeTable.Domain.Information;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryInformationRepository implements InformationRepository {
    private final Map<String, Information> informationMap = new HashMap<>();

    @Override
    public void save(Information information) {
        // ID는 정보의 이름이나 다른 고유한 필드를 사용할 수 있습니다.
        informationMap.put(information.getName(), information);
    }

    @Override
    public Information findByName(String name) {
        return informationMap.get(name);
    }

    @Override
    public List<Information> findAll() {
        return new ArrayList<>(informationMap.values());
    }

    @Override
    public void update(String id, Information updatedInformation) {
        informationMap.put(id, updatedInformation);
    }

    @Override
    public void delete(String id) {
        informationMap.remove(id);
    }
}
