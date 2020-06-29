package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserDevice;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserDeviceRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;

@Service
public class UserDeviceService {

	@Autowired
	private UserDeviceRepository userDeviceRepository;
	
	@Autowired
	private AdRepository adRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public String getUserDeviceToken(Integer userId, Integer adId) {
		String token = "";
		Integer userDeviceId;
		
		if(userId != null) {
			// notification is for owner 
			userDeviceId = userId;
		} else {
			//notification is for booker
			userDeviceId = adRepository.getOne(adId).getUserId().getEntityId();
		}
		
		UserDevice userDevice = userDeviceRepository.findAll().stream().filter(uD -> uD.getUserId().equals(userDeviceId)).findFirst().orElse(null);
		if(userDevice != null) {
			token = userDevice.getDeviceId();
		}
		
		return token;
	}
}
