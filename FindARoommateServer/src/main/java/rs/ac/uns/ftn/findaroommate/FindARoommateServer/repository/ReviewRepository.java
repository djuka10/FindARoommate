package rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
