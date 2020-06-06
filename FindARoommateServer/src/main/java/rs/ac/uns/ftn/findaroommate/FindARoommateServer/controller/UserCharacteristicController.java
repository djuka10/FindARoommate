package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.LanguageService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.UserCharacteristicService;

@RestController
@RequestMapping("/userCharacteristic")
public class UserCharacteristicController {
	
	@Autowired
	private UserCharacteristicService userCharacteristicService;
	
	@GetMapping
	public List<UserCharacteristic> getAll() {
		return userCharacteristicService.getAll();
	}
	
	@GetMapping("/filter")
	public List<UserCharacteristic> getFiltered(@RequestParam CharacteristicType charType) {
		return userCharacteristicService.getFilteredCharacteristic(charType);
	}
	
	@PostMapping
    public String createOrUpdateCity(@RequestBody UserCharacteristic user) {
        

        return "redirect:/city";
    }
	
	
}
