package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto.ReviewDto;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Ad;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Review;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping
	public List<ReviewDto> getAll() {
		List<Review> reviews = reviewService.getAll();
    	return reviews.stream().map(this::createDto).collect(Collectors.toList());
	}
	
	@GetMapping("/{userId}")
	public List<ReviewDto> getAll(@PathVariable Integer userId) {
		List<Review> reviews = reviewService.getUserReviews(userId);
    	return reviews.stream().map(this::createDto).collect(Collectors.toList());
	}
	
	
	@PostMapping
    public ReviewDto createOrUpdateReview(@RequestBody ReviewDto reviewDto) {
		Review review = createEntity(reviewDto);
        return createDto(reviewService.save(review));
    }
	
	private ReviewDto createDto(Review review) {
    	return new ReviewDto(
    			review.getEntityId(), 
    			review.getRating(), 
    			review.getComment(), 
    			review.getTitle(), 
    			review.getAuthor().getFirstName(), review.getAuthor().getEntityId(), review.getRatedUser().getEntityId(), review.getAd().getEntityId());    
    }
	
	private Review createEntity(ReviewDto reviewDto) {
		User author = reviewService.getUser(reviewDto.getAuthor());
		User ratedUser = reviewService.getUser(reviewDto.getRatedUser());
		Ad ad = reviewService.getAd(reviewDto.getAd());

		return Review.builder()
				.ad(ad)
                .author(author)
                .ratedUser(ratedUser)
                .title(reviewDto.getTitle())
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
				.build();
	}
	
}
