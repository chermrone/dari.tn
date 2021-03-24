package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.Appointment;
import tn.dari.spring.service.IAppointmentService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Appointment")

public class AppointmentController {
	@Autowired
	IAppointmentService IA;
	
	
@GetMapping("/all")
public ResponseEntity<List<Appointment>>getAllClaims(){
		System.out.println("reception de la requete");
		List<Appointment> al =IA.GetAllAppointment();
		return new ResponseEntity<List<Appointment>>(al, HttpStatus.OK);
	}

@GetMapping("/find/{id}")
public ResponseEntity<Appointment>Getbyid(@PathVariable("id") Long appointmentId){
	Appointment al = IA.GetAppById(appointmentId);
	return new ResponseEntity<Appointment>(al, HttpStatus.OK);
}

@PostMapping("/add")
public ResponseEntity<Appointment>save(@RequestBody Appointment al){
	List<Appointment> allappointment = IA.GetAllAppointment();
	for (Appointment ia : allappointment) {
		if (ia.getAppointmentId().equals(al.getAppointmentId())) {
			return new ResponseEntity<Appointment>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	Appointment appOne = IA.AddApp(al);
	return new ResponseEntity<Appointment>(appOne, HttpStatus.CREATED);
}
@PutMapping("/update")
public ResponseEntity<Appointment>update(@RequestBody Appointment app){
	Appointment al=IA.UpdateApp(app);
	return new ResponseEntity<Appointment>(al, HttpStatus.OK);
}


@DeleteMapping("/delete/{id}")
public void delete(@PathVariable("id") Long appointmentId){//ResponseEntity<String>
	IA.DeleteApp(appointmentId);
	/*if(IA.GetAppById(appointmentId).getAppointmentId() ==appointmentId)
	return new ResponseEntity<String>("Appointment deleted", HttpStatus.OK);
	else return new ResponseEntity<String>("Appointment not deleted", HttpStatus.NOT_FOUND);
*/}
}