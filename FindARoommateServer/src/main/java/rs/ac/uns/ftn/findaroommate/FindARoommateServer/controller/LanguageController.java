package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.LanguageService;

@RestController
@RequestMapping("/language")
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	
	@GetMapping
	public List<Language> getAll() {
		return languageService.getAll();
	}
	
	
}
