package microservice.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import microservice.model.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
	
	//Device findDeviceByUserId(Long userId);
	//List<Device> findAllById(Long id);
	//Iterable<Device> findAll(Long id);
	List<Device> findAllByEmail(String email);

}
