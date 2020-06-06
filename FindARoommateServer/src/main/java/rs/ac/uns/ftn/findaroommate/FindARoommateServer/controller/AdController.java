package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.AdService;

@RestController
@RequestMapping("/ad")
public class AdController {
	
	@Autowired
	private AdService adService;
	
	@GetMapping
	public List<Ad> testId() {
		return adService.getAll();
	}
	
	
}