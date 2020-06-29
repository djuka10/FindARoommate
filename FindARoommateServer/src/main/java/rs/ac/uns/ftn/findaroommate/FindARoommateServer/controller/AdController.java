package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.ProfileImageDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.AdItem;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.AdService;

@RestController
@RequestMapping("/ad")
public class AdController {
	
	@Autowired
	private AdService adService;
	
	@GetMapping
	public List<Ad> testId() {
		List<Ad> lista = adService.getAll();
		return lista;
	}
	
	@PostMapping("/book")
	public Ad bookAd(@RequestBody Ad ad) {
		return adService.book(ad);
	}
	
	@PostMapping
    public Ad createOrUpdateAd(@RequestBody Ad ad) {
        return adService.save(ad);
    }
	
	@PostMapping("/uploadAdPhoto")
    public ResourceRegistry uploadProfileImage(@ModelAttribute ProfileImageDto model, BindingResult bindingResult) throws IOException {
		
        return adService.uploadAdPhoto(model);
    }
	
	@GetMapping("/adItems/{adId}")
	public List<AdItem> getAdItems(@PathVariable Integer adId) {
		List<AdItem> list = adService.getAdItems(adId);
		return list;
	}
	
	@GetMapping("/userChar/{adId}")
	public List<UserCharacteristic> getUserCharacteristis(@PathVariable Integer adId) {
		List<UserCharacteristic> list = adService.getUserCharacteristis(adId);
		return list;
	}
	
	
}
