package tn.dari.spring.service;

import java.util.List;
import java.util.Map;

import tn.dari.spring.entity.Delivery;
import tn.dari.spring.exception.ResourceNotFoundException;

public interface IDeliveryService {
	public List<Delivery> getAllDelivery();

	public Delivery getDeliveryById(Long ID) throws ResourceNotFoundException;

	public Delivery postDelivery(Delivery delivery);

	public Delivery putDelivery(Long ID, Delivery delivery) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteDelivery(Long ID) throws ResourceNotFoundException;

}
