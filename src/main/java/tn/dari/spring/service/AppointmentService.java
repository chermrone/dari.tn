package tn.dari.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.AdNotFoundException;
import tn.dari.spring.exception.AppointmentNotFoundException;
import tn.dari.spring.repository.AppointmentRepository;



@Service
public class AppointmentService implements IAppointmentService{
	@Autowired
	AppointmentRepository ar;
	@Autowired
	AdService ads;
	@Autowired
	UserService userservice;
	@Autowired
	EmailService es;
	
	@Override
	public List<Appointment> GetAllAppointment(){
	return ar.findAll();	
	}
	@Override
	public Appointment GetAppById (long appointmentId){
		return ar.findById(appointmentId)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment by id= " + appointmentId + " was not found"));
		
		
	}
	@Override
	public Appointment AddApp (Appointment app,long idAd){
		Ad ad =ads.getById(idAd);
		List<User> Us =userservice.GetAllUsers();
		for (User user : Us) {
			Set<Ad> adlist = user.getAds();
			for (Ad ad2 : adlist) {
				if(ad2.equals(ad)){
					app.setLandlord(user);
					return ar.save(app);
				}
			}
			
		}
		
		return null;
	}
	@Override
	public Appointment UpdateApp (Appointment app){
		return ar.save(app);
	}
	@Override
	public String DeleteApp (long appointmentId){
		ar.deleteById(appointmentId);
		return "deleted successfully";
		
	}
	@Override
	public List<Appointment> findByplaceApp(String placeapp) {
		return ar.findByplaceApp(placeapp);
	}
	public List<Appointment> findBydateAppdeb(Date dateAppdeb){
		 return ar.findBydateAppdeb(dateAppdeb);
	 }

	
	
	/*@Override
	public double CalculPourcentage(Long idLandlord, Date dateDebut, Date dateFin) {
		int i=0;
		List<Appointment> app = GetAllAppointment();
		for (Appointment appointment : app) {
			if(appointment.getLandlord().getIdUser()== idLandlord && appointment.getDateAppdeb()==dateDebut && appointment.getDateAppFin()==dateFin)
			
		}
	
		return 0;}
		}*/
	
	@Override
	public List<Appointment> retrieveListAppointmentBYLandlord(long idLandlord) {
		return ar.retrieveListAppointmentBYLandlord(idLandlord);
	}
	//@Transactional
	/*@Override
	public List<Appointment> findByLandlord(long idlandLord); {
		//List<Appointment> app = ar.findByLandlord(idlandLord);
		return ar.findByLandlord(idlandLord);//app.get() ;//ar.findByLandlord(idlandLord);
	}*/
	//cette fonction executer par landlord et le mail envoyee automatiquement
	@Override
	public String AcceptedAppointment(long appointmentId) {
		Optional<Appointment> app = ar.findById(appointmentId);
		if(app.get().isAccepted()==false){
			app.get().setAccepted(true);
			ar.save(app.get());
			User us=userservice.GetUserById(app.get().getUs().getIdUser());
			String subject=("concerning your appointment on"+app.get().getDateAppdeb()+" a " +app.get().getPlaceApp() );
			if(es.sendMail("tuntechdari.tn@gmail.com", us.getEmail() , subject, "votre rendez vous est valide"))
			{return "Accept success and email send"  +app;}
			else{return "Accept success and not email send" +app;}}
		return (" already accepted"+app);
	}

}
