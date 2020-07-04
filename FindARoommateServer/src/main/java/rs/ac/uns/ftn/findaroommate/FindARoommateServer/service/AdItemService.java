package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.AdItem;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdItemRepository;

@Service
public class AdItemService implements ServiceInterface<AdItem> {
	
	@Autowired
	private AdItemRepository adItemRepository;

	@Override
	public List<AdItem> getAll() {
		// TODO Auto-generated method stub
		return adItemRepository.findAll();
	}

	@Override
	public AdItem save(AdItem entity) {
		// TODO Auto-generated method stub
		return adItemRepository.save(entity);
	}

	@Override
	public AdItem getOne(Integer id) {
		// TODO Auto-generated method stub
		return adItemRepository.getOne(id);
	}

	@Override
	public void delete(AdItem entity) {
		// TODO Auto-generated method stub
		adItemRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		adItemRepository.deleteById(id);
	}

}
