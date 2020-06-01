package microservice.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservice.model.AppUser;
import microservice.model.Device;
import microservice.repository.DeviceRepository;
import microservice.repository.UserRepository;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public AppUser save(AppUser appUser) {
		appUser.setEnabled(true);
		userRepository.save(appUser);
		return appUser;
	}

	@Override
	public AppUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
//
//	@Override
//	public List<Device> findAllById(Long id) {
//		List<Device>devices = new ArrayList<>();
//		deviceRepository.findAllById(id).forEach(devices::add);
//        return devices; 
//	}

//	@Override
//	public Device findDeviceByUserId(Long userId) {
//		return deviceRepository.findDeviceByUserId(userId);
//	}

	
	
	

	

}
