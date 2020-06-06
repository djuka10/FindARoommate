package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;

@Service
public class UserService implements ServiceInterface<User> {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User save(User entity) {
		// TODO Auto-generated method stub
		return userRepository.save(entity);
	}

	@Override
	public User getOne(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.getOne(id);
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		userRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

}
