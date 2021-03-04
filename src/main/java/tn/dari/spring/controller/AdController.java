package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.*;

import tn.dari.spring.service.UIadService;

@RestController
@CrossOrigin("*")
public class AdController {
@Autowired UIadService Adserv;
	@GetMapping("ads")
	public ResponseEntity<List<Ad>>getAllSubscriptions(){
		System.out.println("reception de la requete");
		List<Ad> ads = Adserv.getAll();
		return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
	}
}
