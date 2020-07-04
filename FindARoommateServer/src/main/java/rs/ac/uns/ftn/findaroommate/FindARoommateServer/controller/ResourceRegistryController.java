package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.LanguageService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.ResourceRegistryService;

@RestController
@RequestMapping("/resourceRegistry")
public class ResourceRegistryController {
	
	@Autowired
	private ResourceRegistryService resourceRegistryService;
	
	@GetMapping
	public List<ResourceRegistry> getAll() {
		return resourceRegistryService.getAll();
	}
	
	@GetMapping("/ad/{adId}")
	public List<ResourceRegistry> getAdImages(@PathVariable Integer adId) {
		return resourceRegistryService.getAdImages(adId);
	}
	
	
}
