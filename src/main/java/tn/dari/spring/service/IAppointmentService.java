package tn.dari.spring.service;

import java.util.Date;
import java.util.List;

import tn.dari.spring.entity.Appointment;

public interface IAppointmentService {
	
	List<Appointment> GetAllAppointment();
	Appointment GetAppById (long appointmentId);
	Appointment AddApp (Appointment app);
	Appointment UpdateApp (Appointment app);
	void DeleteApp (long appointmentId);
    List<Appointment> findByplaceApp(String placeapp);
    List<Appointment> findBydateApp(Date dateApp);

}
