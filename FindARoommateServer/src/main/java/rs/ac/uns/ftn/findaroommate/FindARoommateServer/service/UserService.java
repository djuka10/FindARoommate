package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.ProfileImageDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Language;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserDevice;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.LanguageRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.ResourceRegistryRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserCharacteristicRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserDeviceRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;

@Service
public class UserService implements ServiceInterface<User> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	@Autowired
	private UserCharacteristicRepository userCharacteristicsRepository;

	@Autowired
	private ResourceRegistryRepository resourceRegistryRepository;
	
	@Autowired
	private UserDeviceRepository userDeviceRepository;
	
	private static final String IMAGE_FOLDER= "images/";
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User save(User entity) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(entity.getEmail());
		User savedUser = null;
		//if exists
		if(user != null) {
			user.setActiveSince(entity.getActiveSince());
			user.setFirstName(entity.getFirstName());
			user.setLastName(entity.getLastName());
			user.setUrlProfile(entity.getUrlProfile());
			
			if(entity.getLanguageIds() != null) {
				List<Language> languages = languageRepository.findAllById(entity.getLanguageIds());
				user.setLanguages(languages);
			}
			
			if(entity.getUserCharacteristicIds() != null) {
				List<UserCharacteristic> userCharacteristics = userCharacteristicsRepository.findAllById(entity.getUserCharacteristicIds());
				user.setCharacteristics(userCharacteristics);
			}
			
			savedUser = userRepository.save(user);
			updateUserDeviceRegistry(savedUser.getEntityId(), entity.getDeviceId());
			removeOneToManyUnnecessary(savedUser);
			return savedUser;
		}
		
		if(entity.getLanguageIds() != null) {
			List<Language> languages = languageRepository.findAllById(entity.getLanguageIds());
			entity.setLanguages(languages);
		}
		
		if(entity.getUserCharacteristicIds() != null) {
			List<UserCharacteristic> userCharacteristics = userCharacteristicsRepository.findAllById(entity.getUserCharacteristicIds());
			entity.setCharacteristics(userCharacteristics);
		}
		
		savedUser = userRepository.save(entity);
		updateUserDeviceRegistry(savedUser.getEntityId(), entity.getDeviceId());
		removeOneToManyUnnecessary(savedUser);
		return savedUser;
	}
	
	private void removeOneToManyUnnecessary(User user) {
		user.setLanguages(null);
		user.setCharacteristics(null);
	}

	@Override
	public User getOne(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.getOne(id);
	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub
		userRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}
	
	public ResourceRegistry uploadProfilePhoto(ProfileImageDto profileImageDto) {
        try {
        	String fileName = profileImageDto.getImage().getOriginalFilename();
        	String fileUrl = IMAGE_FOLDER + fileName;
			    		
		    File outputfile = new File(fileUrl);
		    
		    ByteArrayInputStream bis = new ByteArrayInputStream(profileImageDto.getImage().getBytes());
		    BufferedImage bImage = ImageIO.read(bis);
		    ImageIO.write(bImage, "jpg", outputfile);
		    
		    ResourceRegistry newObj = ResourceRegistry.builder()
		    		.profilePicture(profileImageDto.isProfilePicture())
		    		.user(profileImageDto.getUser())
		    		.uri(fileName)
		    		.addId(-1)
		    		.created(new Date())
		    		.build();
		    return resourceRegistryRepository.save(newObj);
		   
        } catch (IOException e) {
              System.out.println("Exception occured :" + e.getMessage());
        }
		return null;	
	}
	
	public byte[] getImage(@PathVariable String filePath) {
    	String fileUrl = IMAGE_FOLDER + filePath;

	    File outputfile = new File(fileUrl);
	    try {
			byte[] ui = Files.readAllBytes(outputfile.toPath());
			return ui;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void signOut(User userDto) {
		List<UserDevice> userDevices = userDeviceRepository.findAll().stream()
		.filter(uD -> uD.getUserId().equals(userDto.getEntityId()) && uD.getDeviceId().equals(userDto.getDeviceId()))
		.collect(Collectors.toList());
		userDeviceRepository.deleteAll(userDevices);
	}
	
	private void updateUserDeviceRegistry(Integer userId, String deviceId) {
		UserDevice userDevice = UserDevice.builder().userId(userId).deviceId(deviceId).build();
		try {
			userDeviceRepository.save(userDevice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
