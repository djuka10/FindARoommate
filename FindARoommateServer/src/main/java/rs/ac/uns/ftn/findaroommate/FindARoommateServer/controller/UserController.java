package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

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
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
    	String IMAGE_FOLDER= "src/main/resources/images/";

    	String fileUrl = IMAGE_FOLDER + filePath;

	    File outputfile = new File(fileUrl);
	    Path path = outputfile.toPath();
	    String f = path.toString();
	    try {
			BufferedImage bImage = ImageIO.read(outputfile);
			byte[] ui = Files.readAllBytes(outputfile.toPath());
			return ui;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping
    public User createOrUpdateCity(@RequestBody User user) {
        return userService.save(user);
    }
	
	
}
