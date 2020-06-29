package rs.ac.uns.ftn.findaroommate.FindARoommateServer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Review;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.AdRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.ReviewRepository;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository.UserRepository;

@Service
public class ReviewService implements ServiceInterface<Review> {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdRepository adRepository;

	@Override
	public List<Review> getAll() {
		// TODO Auto-generated method stub
		return reviewRepository.findAll();
	}

	@Override
	public Review save(Review object) {
		// TODO Auto-generated method stub
		return reviewRepository.save(object);
	}

	@Override
	public Review getOne(Integer id) {
		// TODO Auto-generated method stub
		return reviewRepository.getOne(id);
	}

	@Override
	public void delete(Review object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Review> getUserReviews(Integer userId) {
		// TODO Auto-generated method stub
		return getAll().stream().filter(r -> 
				r.getAuthor().getEntityId().equals(userId) || r.getRatedUser().getEntityId().equals(userId))
				.collect(Collectors.toList());
	}
	
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.getOne(id);
	}
	
	public Ad getAd(Integer id) {
		// TODO Auto-generated method stub
		return adRepository.getOne(id);
	}

}
