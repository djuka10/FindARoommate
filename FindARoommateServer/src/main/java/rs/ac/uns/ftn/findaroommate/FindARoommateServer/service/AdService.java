package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.ProfileImageDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.AdItem;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdItemRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.ResourceRegistryRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserCharacteristicRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;

@Service
public class AdService implements ServiceInterface<Ad> {
	
	@Autowired
	private AdRepository adRepository;
	
	@Autowired
	private AdItemRepository adItemRepository;
	
	@Autowired
	private UserCharacteristicRepository userCharacteristicsRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResourceRegistryRepository resourceRegistryRepository;
	
	private static final String IMAGE_FOLDER= "images/";

	@Override
	public List<Ad> getAll() {
		// TODO Auto-generated method stub
		return adRepository.findAll();
	}

	@Override
	public Ad save(Ad entity) {
		// TODO Auto-generated method stub
		if(entity.getAdItemsId() != null) {
			List<AdItem> adItems = adItemRepository.findAllById(entity.getAdItemsId());
			entity.setItems(adItems);
		}
		
		if(entity.getRoommatePrefsId() != null) {
			List<UserCharacteristic> userCharacteristics = userCharacteristicsRepository.findAllById(entity.getRoommatePrefsId());
			entity.setCharacteristics(userCharacteristics);
		}
		
		if(entity.getAdOwnerId() != null) {
			User user = userRepository.getOne(entity.getAdOwnerId());
			//TODO: set owner to ad
			entity.setOwnerId(user);
		}
		Ad savedAd = adRepository.save(entity);
		return Ad.builder().entityId(savedAd.getEntityId()).build();
	}

	@Override
	public Ad getOne(Integer id) {
		// TODO Auto-generated method stub
		return adRepository.getOne(id);
	}

	@Override
	public void delete(Ad entity) {
		// TODO Auto-generated method stub
		adRepository.delete(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		adRepository.deleteById(id);
	}
	
	public ResourceRegistry uploadAdPhoto(ProfileImageDto profileImageDto) {
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
		    		.addId(profileImageDto.getAddId())
		    		.uri(fileName)
		    		.created(new Date())
		    		.build();
		    return resourceRegistryRepository.save(newObj);
		   
        } catch (IOException e) {
              System.out.println("Exception occured :" + e.getMessage());
        }
		return null;	
	}

}
