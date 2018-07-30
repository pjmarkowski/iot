package pl.lodz.p.edu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.edu.entity.History;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long>{
    public Iterable<History> findByBikeNumber(String bikeNumber);

    @Query("select bikeNumber from History group by bikeNumber")
     public List<String> getAllBikeNumber();

}
