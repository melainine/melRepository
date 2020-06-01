package microservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import microservice.model.AppUser;
import microservice.model.Device;

@Service
public interface UserService {
	AppUser save(AppUser appUser);

	AppUser findByEmail(String email);
	
	//List<Device> findAllById(Long id);
	
	

	


}
