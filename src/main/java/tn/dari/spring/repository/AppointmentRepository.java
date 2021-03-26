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
	
	// @Query("SELECT u FROM User u WHERE u.role= :role")
	// List<User> retrieveUsersByRole(@Param("role") Role role);
	
	 @Query("SELECT A FROM Appointment  A  WHERE A.landlord.idUser = :idLandlord ")//AND (Appointment.dateAppdeb = :dateDebut) AND (Appointment.dateAppFin= :dateFin) ")
	 public List<Appointment> retrieveListAppointmentBYLandlord(@Param("idLandlord") long idLandlord);//@Param("idlandLord")
	 //public double CalculPourcentage (Long idLandlord , Date dateDebut, Date dateFin);
	 /*@Query("SELECT documentRequestEntity FROM DocumentRequestEntity AS documentRequestEntity "
     + "WHERE (documentRequestEntity.collaborator.id = :collaboratorId) AND (documentRequestEntity.type.id = :typeId) AND (documentRequestEntity.status.label <> :status) ")
public Collection<DocumentRequestEntity> findDocumentRequestsByCollaboratorIdAndTypeId(
        Long collaboratorId , Long typeId ,RequestStatusEnum status);*/
	// @Query("update Appointment A set A.isAccepted = true where A.AppointmentId = :appointmentId")
	// String AcceptedAppointment(@Param("appointmentId")long appointmentId) ;
}
