package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.EmailDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.ProfileImageDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserSettings;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.AdService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.MailService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.SettingsService;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AdService adService;
	
	@Autowired
	private SettingsService userSettingsService;
	
	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}
	
	@GetMapping("/{id}/languages")
	public List<Language> getUserLanguages(@PathVariable Integer id) {
		User user = userService.getOne(id);
		return user.getLanguages();
	}
	
	@GetMapping("/{id}/userCharacteristics")
	public List<UserCharacteristic> getUserCharacteristics(@PathVariable Integer id) {
		User user = userService.getOne(id);
		return user.getCharacteristics();
	}
		
	@PostMapping("/uploadProfilePhoto")
    public ResourceRegistry uploadProfileImage(@ModelAttribute ProfileImageDto model, BindingResult bindingResult) throws IOException {
		
        return userService.uploadProfilePhoto(model);
    }
	
	@GetMapping("/profile/{filePath}")
	public byte[] getImage(@PathVariable String filePath) {
		return userService.getImage(filePath);
	}
	
	@PostMapping
    public User createOrUpdateUser(@RequestBody User user) {
        return userService.save(user);
    }
	
	@PostMapping("/mail")
    public String sendNotificationMail(@RequestBody EmailDto mail, @RequestParam(value = "ad_id", required= false) Integer adId) throws MessagingException {
		//remote mail notification
		if(adId != null) {
			Ad ad = adService.getOne(adId);
			User user = ad.getUserId();
			mail.setSendTo(user.getEmail());
		}
        mailService.send(mail);
        return "Email sent";
    }
	
	@PostMapping("/mailBatch")
    public String sendNotificationMailBatch(@RequestBody List<EmailDto> mails) throws MessagingException {
		for(EmailDto mail: mails) {
			mailService.send(mail);
		}
        return "Email sent";
    }
	
	@PostMapping("/upcomingStays")
    public Integer getUpcomingStays(@RequestBody User userDto, @RequestParam("days_before") Integer daysBefore) {
       User user = userService.getOne(userDto.getEntityId());
       List<Ad> upcomingStays = adService.getUpcomingStays(user.getEntityId(), daysBefore);
       return upcomingStays.size();
    }
	
	@PostMapping("/signOut")
    public String signOut(@RequestBody User userDto){
       userService.signOut(userDto);
       return "SignOut";
    }
	
	@GetMapping("/settings/{userId}")
    public UserSettings getSettings(@PathVariable Integer userId) {
	   UserSettings settings = userSettingsService.getOne(userId);
	   if(settings == null) {
		   settings = new UserSettings();
	   }
       return settings;
    }
	
	@PostMapping("/settings")
    public String updateSettings(@RequestBody UserSettings userSettings){
       userSettingsService.save(userSettings);
       return "Success";
    }
	
}
