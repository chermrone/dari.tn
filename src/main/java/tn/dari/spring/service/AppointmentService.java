package tn.dari.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Appointment;
import tn.dari.spring.repository.AppointmentRepository;



@Service
public class AppointmentService implements IAppointmentService{
	@Autowired
	AppointmentRepository ar;
	
	@Override
	public List<Appointment> GetAllAppointment(){
	return ar.findAll();	
	}
	@Override
	public Appointment GetAppById (long appointmentId){
		Optional<Appointment> app = ar.findById(appointmentId);
		return app.get();
		
	}
	@Override
	public Appointment AddApp (Appointment app){
		return ar.save(app);
	}
	@Override
	public Appointment UpdateApp (Appointment app){
		return ar.save(app);
	}
	@Override
	public void DeleteApp (long appointmentId){
		ar.deleteById(appointmentId);
		
	}
	@Override
	public List<Appointment> findByplaceApp(String placeapp) {
		return ar.findByplaceApp(placeapp);
	}
	public List<Appointment> findBydateApp(Date dateApp){
		 return ar.findBydateApp(dateApp);
	 }
	

}
