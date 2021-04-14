package tn.dari.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.dari.spring.entity.DeliveryMan;
import tn.dari.spring.exception.ResourceNotFoundException;

public interface IDeliveryManService {
	public List<DeliveryMan> getAllDeliveryMan();

	public DeliveryMan getDeliveryManById(Long ID) throws ResourceNotFoundException;

	public DeliveryMan postDeliveryMan(DeliveryMan deliveryMan);

	public DeliveryMan putDeliveryMan(Long ID, DeliveryMan deliveryMan) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteDeliveryMan(Long ID) throws ResourceNotFoundException;
	
	public List<DeliveryMan> getAllDeliveryManByGouvernerat(String gouvernerat);
	
	public List<DeliveryMan> getDeliveryAvailibity();

}
