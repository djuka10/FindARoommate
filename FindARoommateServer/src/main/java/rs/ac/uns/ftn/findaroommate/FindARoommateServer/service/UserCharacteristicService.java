package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.LanguageRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserCharacteristicRepository;

@Service
public class UserCharacteristicService implements ServiceInterface<UserCharacteristic> {
	
	@Autowired
	private UserCharacteristicRepository userCharacteristicRepository;

	@Override
	public List<UserCharacteristic> getAll() {
		// TODO Auto-generated method stub
		return userCharacteristicRepository.findAll();
	}

	@Override
	public UserCharacteristic save(UserCharacteristic entity) {
		// TODO Auto-generated method stub
		return userCharacteristicRepository.save(entity);
	}

	@Override
	public UserCharacteristic getOne(Integer id) {
		// TODO Auto-generated method stub
		return userCharacteristicRepository.getOne(id);
	}

	@Override
	public void delete(UserCharacteristic entity) {
		// TODO Auto-generated method stub
		userCharacteristicRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		userCharacteristicRepository.deleteById(id);
	}
	
	public List<UserCharacteristic> getFilteredCharacteristic(CharacteristicType filter){
		return getAll().stream().filter(uC -> uC.getType().equals(filter)).collect(Collectors.toList());
	}

}
