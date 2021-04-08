package tn.dari.spring.service;

import java.util.Date;
import java.util.List;

import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;

public interface IAppointmentService {
	
	List<Appointment> GetAllAppointment();
	Appointment GetAppById (long appointmentId);
	Appointment AddApp (Appointment app,long idAd);
	Appointment UpdateApp (Appointment app);
	String DeleteApp (long appointmentId);
    List<Appointment> findByplaceApp(String placeapp);
    List<Appointment> findBydateAppdeb(Date dateAppdeb);
    String AcceptedAppointment(long appointmentId) ;
    List<Appointment> retrieveListAppointmentBYLandlord(long idLandlord);
   // double CalculPourcentage (Long idLandlord , Date dateDebut, Date dateFin);

}
