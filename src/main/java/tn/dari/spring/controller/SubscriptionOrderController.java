package tn.dari.spring.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.service.PDFExporter;
import tn.dari.spring.service.SubscriptionOrderService;
import tn.dari.spring.service.UserService;

@RestController
@RequestMapping("/dari/subscriptionorder")
@CrossOrigin(origins ="http://localhost:4200")
public class SubscriptionOrderController {
	@Autowired
	SubscriptionOrderService sos;

	@Autowired
	UserService userservice;

	@Autowired
	RoleRepository rr;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<List<SubscriptionOrdred>> getall() {
		List<SubscriptionOrdred> allsos = sos.GetAll();
		if (allsos.isEmpty()) {
			return new ResponseEntity<List<SubscriptionOrdred>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<SubscriptionOrdred>>(allsos, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> GetById(@PathVariable Long id) {
		SubscriptionOrdred s = sos.GetSubscriptionorder(id);
		return new ResponseEntity<SubscriptionOrdred>(s, HttpStatus.OK);
	}

	@GetMapping("/getbyuser/{iduser}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<List<SubscriptionOrdred>> GetByUser(@PathVariable Long iduser) {
		List<SubscriptionOrdred> usersubsord = sos.GetByUser(iduser);
		if (usersubsord != null) {
			return new ResponseEntity<List<SubscriptionOrdred>>(usersubsord, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/addpremium/{iduser}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> AddPremiumById(@RequestBody SubscriptionOrdred s,
			@PathVariable Long iduser) {
		return new ResponseEntity<>(sos.AddPremiumSubscriptionorder(s, iduser), HttpStatus.OK);
	}

	@PostMapping("/add/{st}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> AddSubscriptionOrder(@RequestBody SubscriptionOrdred s,@PathVariable("st") String st) {
		return new ResponseEntity<>(sos.AddSubscriptionorder(s,st), HttpStatus.OK);
	}
	
	@PostMapping("/addtouser/{st}/{iduser}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubscriptionOrdred> AddSubscriptionOrdertouser(@RequestBody SubscriptionOrdred s,@PathVariable("st") String st,@PathVariable("iduser") Long iduser) {
		return new ResponseEntity<>(sos.AddSubscriptionorder(s,st,iduser), HttpStatus.OK);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubscriptionOrdred> UpdateById(@RequestBody SubscriptionOrdred s) {
		sos.UpdateSubscriptionorder(s);
		return new ResponseEntity<SubscriptionOrdred>(s, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubscriptionOrdred> DeleteById(@PathVariable Long id) {
		sos.deleteSubscriptionOrder(id);
			return new ResponseEntity<SubscriptionOrdred>(HttpStatus.OK);
	}

	@GetMapping("/export/pdf/{id}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public void exportToPDF(HttpServletResponse response, @PathVariable("id") Long id)
			throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=SubscriptionOrder_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		Set<SubscriptionOrdred> orders = userservice.GetUserById(id).getSubscriptions();
		PDFExporter exporter = new PDFExporter(orders);
		exporter.export(response);
	}
}
