package tn.dari.spring.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.AppointmentNotFoundException;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.IAppointmentService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Appointment")

public class AppointmentController {
	@Autowired
	IAppointmentService IA;
	
	
@GetMapping("/all")
public ResponseEntity<List<Appointment>>getAllAppointment(){
		System.out.println("reception de la requete");
		List<Appointment> al =IA.GetAllAppointment();
		return new ResponseEntity<List<Appointment>>(al, HttpStatus.OK);
	}

@GetMapping("/find/{id}")
public ResponseEntity<Appointment>Getbyid(@PathVariable("id") Long appointmentId){
	Appointment al = IA.GetAppById(appointmentId);
	return new ResponseEntity<Appointment>(al, HttpStatus.OK);
}
@GetMapping("/find/landlord/{idLandlord}")
public ResponseEntity<List<Appointment>>Getbyidlandlord(@PathVariable("idLandlord") long idLandlord ){
	List<Appointment> al = IA.retrieveListAppointmentBYLandlord(idLandlord);
	return new ResponseEntity<List<Appointment>>(al, HttpStatus.OK);
}
@GetMapping("/GetNbAppointmentAccepted/landlord/{idLandlord}")
public String NbAppointmentAccepted(@PathVariable("idLandlord") long idLandlord){
	return IA.NbAppointmentAccepted(idLandlord);
}
@GetMapping("/GetNbAppointmentAcceptedByDay/{idLandlord}/{jour}")
public int NbAppointmentAccepted(@PathVariable("idLandlord") long idLandlord,@PathVariable("jour") String jour){
	return IA.NbAppointmentAcceptedbyDay(jour, idLandlord);
}
@GetMapping("/GetNbAppointmentRefusedByDay/{idLandlord}/{jour}")
public int NbAppointmentrefused(@PathVariable("idLandlord") long idLandlord,@PathVariable("jour") String jour){
	return IA.NbAppointmentRefusedbyDay(jour, idLandlord);
}
@GetMapping("/GetNbAppointmentByDay/{idLandlord}/{jour}")
public int NbAppointment(@PathVariable("idLandlord") long idLandlord,@PathVariable("jour") String jour){
	return IA.NbAppointmentbyDay(jour, idLandlord);
}
@GetMapping("/CalculPourcentage/{idLandlord}/{jour}")
public String CalculPourcentage(@PathVariable("idLandlord") long idLandlord,@PathVariable("jour") String jour){
	return IA.CalculPourcentage(idLandlord, jour);
}
@GetMapping("/creationDateofAd/{idad}")
public Date creationDateofAd(@PathVariable("idad")long idad) {
	return  IA.creationDateofAd(idad);}
@GetMapping("/someTips")
public String someTips(){
	return IA.someTips();
}
@PostMapping("/add/{idAd}")
public ResponseEntity<Appointment>save(@RequestBody Appointment al, @PathVariable ("idAd") long idAd ){

	Appointment appOne = IA.AddApp(al,idAd);
	
	return new ResponseEntity<Appointment>(appOne, HttpStatus.CREATED);
}
@PostMapping("/SendSMS")
public String SendSMS(){
	return IA.Appointmentreminde();
}

@PutMapping("/update/{idAd}")
public ResponseEntity<Appointment>update(@RequestBody Appointment app, @PathVariable ("idAd") long idAd){
	Appointment al=IA.UpdateApp(app,idAd);
	return new ResponseEntity<Appointment>(al, HttpStatus.OK);
}
@PutMapping("/update/isAcceptandSendEmail/{appointmentId}")
public String update(@PathVariable ("appointmentId") long appointmentId){
	return IA.AcceptedAppointment( appointmentId);	
}
@PutMapping("/update/isRefuseandSendEmail/{appointmentId}")
public String update1(@PathVariable ("appointmentId") long appointmentId){
	return IA.refusedAppointment(appointmentId)	;
}

@DeleteMapping("/delete/{id}")
public Map<String, Boolean> deleteApp(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException{
	return IA.deleteApp(appointmentId);}
}/*
@DeleteMapping("/delete/{id}")
public Map<String, Boolean> deleteApp(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException{
	return deliveryService.deleteDelivery(ID);
}*/