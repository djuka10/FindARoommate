package rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
