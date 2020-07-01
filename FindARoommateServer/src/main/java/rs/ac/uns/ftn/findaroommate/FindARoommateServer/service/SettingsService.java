package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserSettings;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserSettingsRepository;

@Service
public class SettingsService implements ServiceInterface<UserSettings> {
	
	@Autowired
	private UserSettingsRepository userSettingsRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserSettings> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSettings save(UserSettings object) {
		// TODO Auto-generated method stub
		UserSettings dbConfig = getOne(object.getUserId());
		if(dbConfig != null) {
			dbConfig.setNewValues(object);
			return userSettingsRepository.save(dbConfig);
		}
		User user = userRepository.getOne(object.getUserId());
		object.setUserId(user.getEntityId());
		return userSettingsRepository.save(object);
	}

	@Override
	public UserSettings getOne(Integer id) {
		return userSettingsRepository.findByUserId(id);
	}

	@Override
	public void delete(UserSettings object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
