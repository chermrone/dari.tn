package tn.dari.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.AppointmentNotFoundException;

public interface IAppointmentService {
	
	List<Appointment> GetAllAppointment();
	Appointment GetAppById (long appointmentId);
	Appointment AddApp (Appointment app,long idAd);
	Appointment UpdateApp (Appointment app,long idad);
	//String DeleteApp (long appointmentId);
	public Map<String, Boolean> deleteApp(long appointmentId)throws AppointmentNotFoundException;
    List<Appointment> findByplaceApp(String placeapp);
    List<Appointment> findBydateAppdeb(Date dateAppdeb);
    String AcceptedAppointment(long appointmentId) ;
    String refusedAppointment(long appointmentId);
    List<Appointment> retrieveListAppointmentBYLandlord(long idLandlord);
    String NbAppointmentAccepted(long idLandlord);
    int NbAppointmentAcceptedbyDay(String jour,long idLandlord);
    int NbAppointmentRefusedbyDay(String jour,long idLandlord);
    int NbAppointmentbyDay(String jour, long idLandlord);
    String CalculPourcentage (Long idLandlord , String jour);
    String Appointmentreminde();
    Date creationDateofAd (long idad);
	String someTips();

}
