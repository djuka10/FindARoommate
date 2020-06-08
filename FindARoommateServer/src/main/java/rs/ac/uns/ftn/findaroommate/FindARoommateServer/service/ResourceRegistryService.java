package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.ResourceRegistryRepository;

@Service
public class ResourceRegistryService implements ServiceInterface<ResourceRegistry> {
	
	@Autowired
	private ResourceRegistryRepository resourceRegistryRepository;

	@Override
	public List<ResourceRegistry> getAll() {
		// TODO Auto-generated method stub
		return resourceRegistryRepository.findAll();
	}

	@Override
	public ResourceRegistry save(ResourceRegistry object) {
		// TODO Auto-generated method stub
		return resourceRegistryRepository.save(object);
	}

	@Override
	public ResourceRegistry getOne(Integer id) {
		// TODO Auto-generated method stub
		return resourceRegistryRepository.getOne(id);
	}

	@Override
	public void delete(ResourceRegistry object) {
		// TODO Auto-generated method stub
		resourceRegistryRepository.delete(object);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		resourceRegistryRepository.deleteById(id);
	}
	
	public List<ResourceRegistry> getAdImages(Integer adId) {
		// TODO Auto-generated method stub
		return getAll().stream().filter(r -> r.getAddId().equals(adId)).collect(Collectors.toList());
	}

}
