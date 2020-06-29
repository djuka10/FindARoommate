package rs.ac.uns.ftn.findaroommate.FindARoommateServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.AdItem;
import rs.ac.uns.ftn.findaroommate.FindARoommateServer.model.UserDevice;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {

}
