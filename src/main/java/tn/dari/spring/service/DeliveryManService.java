package tn.dari.spring.service;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Delivery;
import tn.dari.spring.entity.DeliveryMan;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.DeliveryManRepository;

@Service
public class DeliveryManService implements IDeliveryManService{
	
	@Autowired
	DeliveryManRepository deliveryManRepository;

	@Override
	public List<DeliveryMan> getAllDeliveryMan() {

		return deliveryManRepository.findAll();
	}

	@Override
	public DeliveryMan getDeliveryManById(Long ID) throws ResourceNotFoundException {
		DeliveryMan deliveryMan = deliveryManRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		return deliveryMan;
	}

	@Override
	public DeliveryMan postDeliveryMan(DeliveryMan deliveryMan) {


		deliveryManRepository.save(deliveryMan);
		return deliveryMan;
	}

	@Override
	public DeliveryMan putDeliveryMan(Long ID, DeliveryMan deliveryMan) throws ResourceNotFoundException {
		if (deliveryMan.getDeliveryManID() == ID) {
			DeliveryMan deliveryMan1 = deliveryManRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			
			deliveryManRepository.save(deliveryMan);
		}
		return deliveryMan;
	}

	@Override
	public Map<String, Boolean> deleteDeliveryMan(Long ID) throws ResourceNotFoundException {
		DeliveryMan deliveryMan = deliveryManRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		deliveryManRepository.delete(deliveryMan);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

	@Override
	public List<DeliveryMan> getAllDeliveryManByGouvernerat(String gouvernerat) {
		
		return deliveryManRepository.findByGouvernerat(gouvernerat);
	}

	@Override
	public List<DeliveryMan> getDeliveryAvailibity() {
		
		Date date = new Date();
		List<DeliveryMan> list = deliveryManRepository.findAll();
		List<DeliveryMan> newlist = new ArrayList();
		
		for(DeliveryMan o : list ){
			int nbre=0;
			Set<Delivery> list1 = o.getDeliveries();
			for(Delivery e : list1){
				if(e.getDate().equals(date))
					nbre++;
			}
			if(nbre<=10){newlist.add(o);}
			
		}
		
		return newlist;
	}
	
	
	
	
	
	
	/*@Override
	public DeliveryMan getRandomDeliveryMan() throws Exception {

		// The python classpath is usually set by environment variable
		// or included in the java project classpath but it can also be set
		// programmatically. Here I hard code it just for the example.
		// This is not required if we use jython standalone JAR
		//PySystemState systemState = Py.getSystemState();
		//systemState.path.append(new PyString("C:\\jython2.7.1\\Lib"));

		// Here is the actual code that interprets our python file.
		PythonInterpreter interpreter = new PythonInterpreter();
		String path = Paths.get("").toAbsolutePath().toString();
		interpreter.execfile(path+"..\\py\\DeliveryManServicePython.py");
		PyObject buildingObject = interpreter.get("DeliveryManServicePython").__call__();
		interpreter.close();

		// Cast the created object to our Java interface
		return (HelloService) buildingObject.__tojava__(HelloService.class);
	}*/

}
