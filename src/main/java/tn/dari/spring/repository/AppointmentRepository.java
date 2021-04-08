package tn.dari.spring.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.User;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	 List<Appointment> findByplaceApp(String placeapp);
	 List<Appointment> findBydateAppdeb(Date dateAppdeb);
	
	
	
	 @Query("SELECT A FROM Appointment  A  WHERE A.landlord.idUser = :idLandlord ")//AND (Appointment.dateAppdeb = :dateDebut) AND (Appointment.dateAppFin= :dateFin) ")
	 public List<Appointment> retrieveListAppointmentBYLandlord(@Param("idLandlord") long idLandlord);
	// @Query("update Appointment A set A.isAccepted = true where A.AppointmentId = :appointmentId")
	// String AcceptedAppointment(@Param("appointmentId")long appointmentId) ;
	 @Query("SELECT count(A) FROM Appointment  A  WHERE (A.jour = :jour)AND (A.isAccepted =true) AND (A.landlord.idUser = :idLandlord)")
	 public int NbAppointmentAcceptedbyDay(@Param("jour")String jour,@Param("idLandlord") long idLandlord);
	 
	 @Query("SELECT count(A) FROM Appointment  A  WHERE (A.jour = :jour)AND (A.refused =true) AND (A.landlord.idUser = :idLandlord)")
	 public int NbAppointmentRefusedbyDay(@Param("jour")String jour,@Param("idLandlord") long idLandlord);
	 
	 @Query("SELECT count(A) FROM Appointment  A  WHERE (A.jour = :jour) AND (A.landlord.idUser = :idLandlord)")
	 public int NbAppointmentbyDay(@Param("jour")String jour ,@Param("idLandlord") long idLandlord);
	 
	 @Query("SELECT count(A) FROM Appointment  A  WHERE (A.landlord.idUser = :idLandlord)AND (A.isAccepted =true) ")
	 public int NbAppointmentAccepted(@Param("idLandlord")long idLandlord);
}
