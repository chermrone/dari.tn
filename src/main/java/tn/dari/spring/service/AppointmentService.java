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
					app.setJour(findDay(app.getDateAppdeb()));
					return ar.save(app);
				}
			}
			
		}
		
		return null;
	}
	public String findDay(Date DateAppdeb){
		String jour="";
		if(DateAppdeb.getDay()==0)
			jour = "Sunday";
		else if(DateAppdeb.getDay()==1)
			jour ="Monday";
		else if(DateAppdeb.getDay()==2)
			jour ="Tuesday";
		else if(DateAppdeb.getDay()==3)
			jour ="Wednesday";
		else if(DateAppdeb.getDay()==4)
			jour ="Thursday";
		else if(DateAppdeb.getDay()==5)
			jour ="Friday";
		else if(DateAppdeb.getDay()==6)
			jour ="Saturday";
		return jour;
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

	
	
	@Override
	public String CalculPourcentage(Long idLandlord, String jour) {
		float a =NbAppointmentAcceptedbyDay(jour,idLandlord);
		System.out.println(a);
		float r=NbAppointmentRefusedbyDay(jour, idLandlord);
		System.out.println(r);
		float total=NbAppointmentbyDay(jour, idLandlord);
		System.out.println(total);
		//int total=ar.NbAppointmentAccepted(idLandlord);
		if(total!=0)
		{float Pa;
		Pa=((a/total)*100);
		System.out.println(Pa);
		float Pr=((r/total)*100);
		System.out.println(Pr);
		return "le pourcentage de l'acceptation du rendez vous dans cette jour est :"+Pa+"%  le pourcentage que le rendez vous refuser est :"+Pr+"%";
		}
			return "jour vide";}
	
	@Override
	public List<Appointment> retrieveListAppointmentBYLandlord(long idLandlord) {
		return ar.retrieveListAppointmentBYLandlord(idLandlord);
	}
	
	//cette fonction executer par landlord et le mail envoyee automatiquement
	@Override
	public String AcceptedAppointment(long appointmentId) {
		Optional<Appointment> app = ar.findById(appointmentId);
		if(app.get().isAccepted()==false){
			app.get().setAccepted(true);
			app.get().setRefused(false);
			ar.save(app.get());
			User us=userservice.GetUserById(app.get().getUs().getIdUser());
			String subject=("concerning your appointment on"+app.get().getDateAppdeb()+" a " +app.get().getPlaceApp() );
			if(es.sendMail("tuntechdari.tn@gmail.com", us.getEmail() , subject, "votre rendez vous est valide"))
			{return "Accept success and email send"  +app;}
			else{return "Accept success and not email send" +app;}}
		return (" already accepted"+app);
	}
	@Override
	public String NbAppointmentAccepted(long idLandlord) {
		int a =ar.NbAppointmentAccepted(idLandlord);
		return ("the number of appointments accepted by " +userservice.GetUserById(idLandlord).getUserName()+" = "+a);
	}
	//cette fonction executer par landlord et le mail envoyee automatiquement
	@Override
	public String refusedAppointment(long appointmentId) {
		Optional<Appointment> app = ar.findById(appointmentId);
		if(app.get().isRefused()==false){
			app.get().setRefused(true);
			app.get().setAccepted(false);
			ar.save(app.get());
			User us=userservice.GetUserById(app.get().getUs().getIdUser());
			String subject=("concerning your appointment on"+app.get().getDateAppdeb()+" a " +app.get().getPlaceApp() );
			if(es.sendMail("tuntechdari.tn@gmail.com", us.getEmail() , subject, "your appointment is refused"))
			{return "refuse success and email send"  +app;}
			else{return "refuse success and not email send" +app;}}
		return (" already refused"+app);
	}
	
	@Override
	public int NbAppointmentAcceptedbyDay(String jour, long idLandlord) {
		return ar.NbAppointmentAcceptedbyDay(jour, idLandlord);
	}
	@Override
	public int NbAppointmentRefusedbyDay(String jour, long idLandlord) {
		return ar.NbAppointmentRefusedbyDay(jour, idLandlord);
	}
	@Override
	public int NbAppointmentbyDay(String jour, long idLandlord) {
		return ar.NbAppointmentbyDay(jour, idLandlord);
	}
	
}
