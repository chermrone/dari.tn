package tn.dari.spring.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.el.parser.ParseException;

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
	@Autowired
	SMSservice sms;
	
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
					app.setIdAd(idAd);
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
	public Appointment UpdateApp (Appointment app,long idad){
		app.setJour(findDay(app.getDateAppdeb()));
		app.setIdAd(idad);
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
	@Override
	public String Appointmentreminde() {
		List<Appointment> apps = ar.findAll();
		if(apps!=null){
		for (Appointment appointment : apps) {
			System.out.println( getLocalDate());
			System.out.println(appointment.getDateAppdeb());
			System.out.println(appointment.getDateAppdeb().getDay());
			System.out.println(appointment.getJour());
			System.out.println(convertToDateViaInstant(getLocalDate()).getDate());
			System.out.println(appointment.getDateAppdeb().getDate()-convertToDateViaInstant(getLocalDate()).getDate());
			System.out.println(appointment.getDateAppdeb().getMonth()==convertToDateViaInstant(getLocalDate()).getMonth());
			System.out.println(appointment.isAccepted());
			User us=userservice.GetUserById(appointment.getUs().getIdUser());
			String to = "+216"+us.getPhoneNumber();
			String msg ="don't forget your appointment on "+appointment.getJour() +appointment.getDateAppdeb() +" in "+appointment.getPlaceApp()+" with " +appointment.getLandlord().getFirstName();
			if((( appointment.getDateAppdeb().getDate()-convertToDateViaInstant(getLocalDate()).getDate())==1) && (appointment.isAccepted()==true) && (sms.send( to , msg) ==true) && (appointment.getDateAppdeb().getYear()==convertToDateViaInstant(getLocalDate()).getYear()) && (appointment.getDateAppdeb().getMonth()==convertToDateViaInstant(getLocalDate()).getMonth())){
				//& appointment.getDateAppdeb().getYear()==convertToDateViaInstant(getLocalDate()).getYear() & appointment.getDateAppdeb().getMonth()==convertToDateViaInstant(getLocalDate()).getMonth()
					return getTimeStamp() + ": SMS has been sent!: to"+to;}else
					
			return getTimeStamp() + ": SMS not sent!: to";}}return "pas de rdv";}
			
	 private String getTimeStamp() {
	       return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	    }
 
private LocalDateTime getLocalDate(){
	return LocalDateTime.now();
}
	private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	    	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	    	      .toInstant());
	    	}
	@Override
	public Date creationDateofAd(long idad) {
		Ad ad =ads.getById(idad);
		return ad.getCreationDate();}
	public String TitleAd(long idad) {
		Ad ad =ads.getById(idad);
		return ad.getTitleAd();}
	
	        private Date after2Month;
			private Date getafter2Month(){
				return after2Month;
			}
			@Override
			public String someTips(){
				List<Ad> apps = ads.getAll();
				if(apps!=null){
				for (Ad ad : apps) 
				{
					Date creationDateofAd =creationDateofAd(ad.getAdId());
					  Calendar cal = Calendar.getInstance();
					     //Définir la date
					     cal.setTime(creationDateofAd);
					  //Nombre de jours à ajouter
					  cal.add(Calendar.DAY_OF_MONTH, 60); //DAY_OF_MONTH 
					  //Date après avoir ajouté les jours à la date indiquée
					  Date after2Month = cal.getTime();  
					  System.out.println("Date après l'addition: "+after2Month);
					//Date after2Month = creationDateofAd;
				   // after2Month.setMonth((creationDateofAd.getMonth())+2);
				    System.out.println(after2Month);
				    System.out.println(creationDateofAd);
				    if( convertToDateViaInstant(getLocalDate()).getMonth()-creationDateofAd.getMonth()==2 & convertToDateViaInstant(getLocalDate()).getYear()== creationDateofAd.getYear()){
					long nbAppoitment =ar.NBAppointment(creationDateofAd,after2Month,ad.getAdId());
					 if(nbAppoitment ==0){
						User us=userservice.GetUserById(ad.getUs().getIdUser());
						String subject=("your ad with title:"+TitleAd(ad.getAdId())+ "add a:"+creationDateofAd(ad.getAdId()) );
						String msg ="there have been no appointments for your ad for two months please if you can reduce the price of this ad or check the characteristics of your ad";
						if(es.sendMail("tuntechdari.tn@gmail.com", us.getEmail() , subject, msg)){
							return " email send"  ;}
						 return "  email not send" ;}
					 return " NBAppointment different 0" ;}return "period inferieur a 2 mois";}
							
						}return "pas de rdv";}
		
		
		
		
	

}
