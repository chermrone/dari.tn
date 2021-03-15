package tn.dari.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	 List<Appointment> findByplaceApp(String placeapp);
	 List<Appointment> findBydateApp(Date dateApp);
}
