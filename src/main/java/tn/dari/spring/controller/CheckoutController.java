package tn.dari.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Order;
import com.stripe.model.Product;
import com.stripe.model.Sku;
import com.stripe.model.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.dto.GeoIP;
import tn.dari.spring.entity.CardInfo;
import tn.dari.spring.entity.Delivery;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.FournitureAdRepository;
import tn.dari.spring.repository.OrderUserRepository;
import tn.dari.spring.service.DeliveryService;
import tn.dari.spring.service.RawDBDemoGeoIPLocationService;
import tn.dari.spring.service.ShoppingCartService;
import tn.dari.spring.service.StripeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dari")
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String STRIPE_PUBLIC_KEY;
    @Value("${STRIPE_SECRET_KEY}")
    private String STRIPE_SECRET_KEY;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    RawDBDemoGeoIPLocationService rawDBDemoGeoIPLocationService;
    @Autowired
    private OrderUserRepository orderUserRepository;
    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    Float totalPrice = 0F;

    /*
     * @RequestMapping("/authenticateStripe") public Boolean authenticateStripe() {
     * RequestOptions requestOptions = RequestOptions.builder().setApiKey(
     * "sk_test_51IZax5LfpDUvCc4a6hBuiR2dCiNgk5nmvCKlnYTFsrIllu68RlucWgjJd5hV4MJKDTEtEHEyVhJ2NVmcVs83qg5900E0V0DBl3"
     * ).build();
     * 
     * try { Charge charge =
     * Charge.retrieve("ch_1IZbqsLfpDUvCc4af32q4Mip",requestOptions);
     * System.out.println("---------------------"); System.out.println(charge); }
     * catch (StripeException e) {
     * 
     * e.printStackTrace(); }
     * 
     * return false; }
     */

    @GetMapping("/checkout/{orderId}")
    public ResponseEntity<Object> checkout(@PathVariable(name = "orderId") Long orderId) {
        OrderUser orderUser = orderUserRepository.findById(orderId).get();
        List<Object> items = new ArrayList<>();
        OrderUser updatedOrder = null;
        try {
            Stripe.apiKey = STRIPE_SECRET_KEY;
            for (FournitureAd fa : orderUser.getShoppingCart().getFournitureAds()) {
                // Product Creation
                Map<String, Object> params = new HashMap<>();
                params.put("active", fa.getAvailable().booleanValue());
                params.put("name", fa.getNameFa());
                params.put("description", fa.getDescription());
                params.put("type", "good");
                Product product = Product.create(params);
                // SKU Creation
                params = new HashMap<>();
                params.put("price", fa.getPrice().intValue()*100);
                params.put("currency", "eur");
                params.put("active", fa.getAvailable().booleanValue());
                params.put("product", product.getId());
                Map<String, Object> inventory = new HashMap<>();
                inventory.put("type", "finite");
                inventory.put("quantity",1);
                params.put("inventory", inventory);
                Sku sku = Sku.create(params);
                Map<String, Object> item = new HashMap<>();
                item.put("type", "sku");
                item.put("parent", sku.getId());
                items.add(item);
                log.info("Items were created");
            }
            // Order Creation
            Map<String, Object> address = new HashMap<>();
            address.put("line1", orderUser.getShoppingCart().getAddress());
            Map<String, Object> shipping = new HashMap<>();
            shipping.put("name", orderUser.getShoppingCart().getUs().getFirstName()+" "+ orderUser.getShoppingCart()
                    .getUs().getLastName() );
            shipping.put("address", address);            
            Map<String, Object> params = new HashMap<>();
            params.put("currency", "eur");
            params.put("email", orderUser.getShoppingCart().getUs().getEmail());
            params.put("items", items);
            params.put("shipping", shipping);
            log.info("Order was populated");
            Order order = Order.create(params);
            orderUser.setStripeOrder(order.getId());            
            for (FournitureAd fa : orderUser.getShoppingCart().getFournitureAds()) {
                fa.setAvailable(false);
            }
            updatedOrder = orderUserRepository.save(orderUser);
            log.info("Order was created, total amount:" + order.getAmount());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(updatedOrder);
    }

    @PostMapping("/charge/{orderId}")
    public ResponseEntity<Object> charge(@RequestBody CardInfo cardInfo,@PathVariable(name = "orderId") Long orderId , @RequestParam(value = "ipAddress", required = true) String ipAddress) {
        try {
            log.info("ip:"+ipAddress);
            log.info("cardInfo:"+cardInfo);
            log.info("orderId:"+orderId);
            OrderUser orderUser = orderUserRepository.findById(orderId).get();
            Map<String, Object> card = new HashMap<>();
            card.put("number", cardInfo.getNumber());
            card.put("exp_month", cardInfo.getExp_month());
            card.put("exp_year", cardInfo.getExp_year());
            card.put("cvc", cardInfo.getCvc());
            Map<String, Object> params = new HashMap<>();
            params.put("card", card);
            Token token = Token.create(params);
            Order order = Order.retrieve(orderUser.getStripeOrder());
            params = new HashMap<>();
            params.put("source", token.getId());
            order.pay(params);
            orderUser.setStatusOrd(true);
            Delivery delivery = new Delivery();
            delivery.setDate(new Date());
            delivery.setStatus(true);
            delivery.setCost(order.getAmount()+order.getAmount()*0.1);
            GeoIP geoIP = null;
            String city = "tunis";
			try {
				log.info("request.getRemoteAddr()"+ipAddress);
				geoIP = rawDBDemoGeoIPLocationService.getLocation(ipAddress);                
                if(geoIP.getCity() !=null){
                    city = geoIP.getCity();
                }
			} catch (IOException e) {				
				e.printStackTrace();
                city = "tunis";

			} catch (GeoIp2Exception e) {				
				e.printStackTrace();
                city = "tunis";
			}
            log.info("city:"+city);
            delivery.setPlace(city);
            delivery.setDeliveryMan(deliveryService.affectDeliveryMan(delivery, geoIP).getDeliveryMan());
                        
            orderUserRepository.save(orderUser);
            delivery.setOrderUser(orderUser);
            deliveryService.postDelivery(delivery);
            ShoppingCart shoppingCart = orderUser.getShoppingCart();
            shoppingCart.setFournitureAds(new HashSet<>());
            try {
                shoppingCartService.putShoppingCart(shoppingCart.getShoppingCartId(), shoppingCart);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
                log.error("error while updatting shopping cart");
            }
            /* log.info("order in charge: "+orderUser);
            log.info("delivery in charge: "+delivery); */
            return ResponseEntity.ok().body(orderUser);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}