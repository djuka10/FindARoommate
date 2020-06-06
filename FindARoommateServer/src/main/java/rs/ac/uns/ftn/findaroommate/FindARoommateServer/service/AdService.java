package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdRepository;

@Service
public class AdService implements ServiceInterface<Ad> {
	
	@Autowired
	private AdRepository adRepository;

	@Override
	public List<Ad> getAll() {
		// TODO Auto-generated method stub
		return adRepository.findAll();
	}

	@Override
	public Ad save(Ad entity) {
		// TODO Auto-generated method stub
		return adRepository.save(entity);
	}

	@Override
	public Ad getOne(Integer id) {
		// TODO Auto-generated method stub
		return adRepository.getOne(id);
	}

	@Override
	public void delete(Ad entity) {
		// TODO Auto-generated method stub
		adRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		adRepository.deleteById(id);
	}

}
