package tn.dari.spring.service;

import java.util.Date;
import java.util.List;

import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;

public interface IAppointmentService {
	
	public List<Appointment> GetAllAppointment();
	public Appointment GetAppById (long appointmentId);
	public Appointment AddApp (Appointment app,long idAd);
	public Appointment UpdateApp (Appointment app);
	public String DeleteApp (long appointmentId);
	public List<Appointment> findByplaceApp(String placeapp);
	public List<Appointment> findBydateAppdeb(Date dateAppdeb);
	public String AcceptedAppointment(long appointmentId) ;
	public List<Appointment> retrieveListAppointmentBYLandlord(long idLandlord);
   // public double CalculPourcentage (Long idLandlord , Date dateDebut, Date dateFin);

}
