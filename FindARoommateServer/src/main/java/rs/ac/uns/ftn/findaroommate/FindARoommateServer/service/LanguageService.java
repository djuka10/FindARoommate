package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.LanguageRepository;

@Service
public class LanguageService implements ServiceInterface<Language> {
	
	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public List<Language> getAll() {
		// TODO Auto-generated method stub
		return languageRepository.findAll();
	}

	@Override
	public Language save(Language object) {
		// TODO Auto-generated method stub
		return languageRepository.save(object);
	}

	@Override
	public Language getOne(Integer id) {
		// TODO Auto-generated method stub
		return languageRepository.getOne(id);
	}

	@Override
	public void delete(Language object) {
		// TODO Auto-generated method stub
		languageRepository.delete(object);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		languageRepository.deleteById(id);
	}

}
