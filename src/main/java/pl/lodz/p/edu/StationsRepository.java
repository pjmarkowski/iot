package pl.lodz.p.edu;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.edu.entity.Stations;

public interface StationsRepository extends CrudRepository<Stations, Long>{
public Iterable<Stations> findByStationNumber(String stationNumber);

}
