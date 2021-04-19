package tn.dari.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.TypeBatiment;
import tn.dari.spring.enumeration.Typead;
import tn.dari.spring.enumeration.Usertype;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and  a.city= :city")
	public float retrieveSellsAdsBycity(@Param("city") String role);
	
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and a.city= :city and a.price< :price")
	public float retrieveSellsAdsByCityMaxPrice(@Param("city") String role, @Param("price") double price);
	
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and a.city= :city and a.price> :price")
	public float retrieveSellsAdsByCityMinPrice(@Param("city") String role, @Param("price") double price);

	@Query("SELECT count(a) FROM Ad a WHERE a.city= :city and a.sell=true and  DATEDIFF(a.BuyingDate,a.creationDate)< :period")
	public float retrieveSellsAdsInPeriod(@Param("city") String city, @Param("period") int period);

	
	////////////////////////////////////////////////////retrieve ad depending on role//////////////////////////////////////////////////////////

	@Query("SELECT u from Ad u JOIN  u.us uss "+ "join uss.roles r WHERE r.name=:role")
	public List<Ad> retriveAdDependingOnRole(@Param("role") Usertype role);

	
	////////////////////////////////////////////////count favorites for an ad//////////////////////////////////////////////////////////

	@Query(nativeQuery = true, value ="Select count(*) From user,ad,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And favorite=:id")
	public int retriveNumberOffavoritesForPremium(@Param("id") long id);
	//////////////////////////////////////retrieve Ad By Banned User(by Role) in specific date//////////////////////////////////////////////////////////

	@Query("SELECT u from Ad u JOIN  u.us uss "+ "join uss.roles r "
			+ "WHERE uss.userState=false  and uss.banDate between :mindate  and :maxdate  "
			+ "and r.id=:role")
	public List<Ad> retrieveAdByBannedUser(@Param("role") Long role,@Param("maxdate") Date maxdays,@Param("mindate") Date mindays);
	//////////////////////////////////////////////////check the ad is banned //////////////////////////////////////////////////////////


	@Query("select count(u) > 0 from Ad  u join u.us uss where uss.userState=false and  u.adId = :id")
public boolean CheckAdBanned(@Param("id")long id);
	////////////////////////////////////////////check the user connected is admin or not//////////////////////////////////////////////////////////

	@Query("SELECT count(u)>0 from User u "+ 
"join u.roles r WHERE u=:user and r.name=tn.dari.spring.enumeration.Usertype.ADMIN")
	public boolean CheckExistingRoleADMINforConsulterOfAd(@Param("user") User user);

	////////////////////////////////////////////////////Estimation price//////////////////////////////////////////////////////////

	/////####Ground
	@Query(nativeQuery = true, value ="Select :area*city.pricemetre From user,city,user_roles "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true "
			+ "and user_roles.user_id=:#{#user.idUser} "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='ground' and user_roles.role_id=5  ")
	public double RetrievEstimatedPriceGround(@Param("area")double area,@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	
	/////####appartment
	////////////////////////////::::Non Famous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(:builda*city.price_build , 2) From user,city"
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='apartment'and city.famous=false ")
	public double RetrievEstimatedPriceNONFamousRegionAppartment(@Param("builda")double builda,@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	///////Update table cities :column famous if top five =1
	@Transactional
	@Modifying
	@Query("Update City u set u.famous=true where u.name in (:listfamous)")
	public void UpdateCitiesFamous(@Param("listfamous") List<String> listfamous);
	////////////////////////////::::Famous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(:builda*city.price_build*1.75, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='apartment' ")
	public double RetrievEstimatedPriceFamousRegionAppartment(@Param("builda")double builda,@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	
	/////####house
	////////////////////////////::::Famous::::////////////////////////
	@Query(nativeQuery = true, value ="Select ROUND((:builda*city.price_build)*1.75+(:area-:builda)*city.pricemetre, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='house' ")
	public double RetrievEstimatedPriceFamousRegionHouse(@Param("builda")double builda,@Param("area")double area,
			@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	////////////////////////////::::NonFamous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND((:builda*city.price_build)+(:area-:builda)*city.pricemetre, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=false "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='house' ")
	public double RetrievEstimatedPriceNonFamousRegionHouse(@Param("builda")double builda,@Param("area")double area,
			@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	/////####RENT
	
	////////////////////////////::::NonFamous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=false "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceNonFamousRegionOnlyRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	
	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.7, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=false "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceNonFamousRegionMultiRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	////////////////////////////::::Famous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.8, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceFamousRegionOnlyRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	
	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.5*1.1, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceFamousRegionMultiRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);



////////////////////////////////////////////////////Estimation period for selling house//////////////////////////////////////////////////////////
////////////////////////////::::Update table city ::::////////////////////////

@Transactional
@Modifying
@Query("Update City u set u.EstimationDuration ="
		+ "CASE WHEN u.famous=true THEN 30 WHEN u.famous=false THEN 120 END where u.name=:city"
		)
public void UpdateEstimatedPeriodSellHouse(@Param("city") String city);

////////////////////////////::::Ideal Season + price entred < estimated::::////////////////////////



@Query(nativeQuery = true, value ="Select city.estimation_duration From user,city,user_roles "
		+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and user_roles.role_id=5 "
		+ " And city.name=Lower(:city)  and :price_entered-:price_estimated<0 and "
		+ " MONTH(CURRENT_TIMESTAMP) Not in (9,10,11,12,1,2)")
public int RetrievEstimatedPeriodIdealCities(@Param("city") String city,@Param("price_entered") double price_entered,@Param("price_estimated") double price_estimated,@Param("user")User user);

////////////////////////////::::non Ideal Season + price entred > estimated::::////////////////////////


@Query(nativeQuery = true, value ="Select city.estimation_duration+90 From user,city,user_roles "
		+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and user_roles.role_id=5 "
		+ " And city.name=Lower(:city)  and :price_entered-:price_estimated>0 and "
		+ " MONTH(CURRENT_TIMESTAMP) in (9,10,11,12,1,2)")
public int RetrievEstimatedPeriodNonIdealCities(@Param("city") String city,@Param("price_entered") double price_entered,@Param("price_estimated") double price_estimated,@Param("user")User user);


////////////////////////////::::non Ideal Season + price entred < estimated::::////////////////////////

@Query(nativeQuery = true, value ="Select city.estimation_duration+30 From user,city,user_roles "
		+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and user_roles.role_id=5 "
		+ " And city.name=Lower(:city)  and :price_entered-:price_estimated<0 and "
		+ " MONTH(CURRENT_TIMESTAMP) in (9,10,11,12,1,2)")
public int RetrievEstimatedPeriodNonIdealCitiesWinterSeason(@Param("city") String city,@Param("price_entered") double price_entered,@Param("price_estimated") double price_estimated,@Param("user")User user);


////////////////////////////::::Ideal Season + price entred > estimated::::////////////////////////

@Query(nativeQuery = true, value ="Select city.estimation_duration From user,city,user_roles "
		+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and user_roles.role_id=5 "
		+ " And city.name=Lower(:city)  and :price_entered-:price_estimated>0 and "
		+ " MONTH(CURRENT_TIMESTAMP) Not in (9,10,11,12,1,2)")
public int RetrievEstimatedPeriodNonIdealCitiesOverPrice(@Param("city") String city,@Param("price_entered") double price_entered,@Param("price_estimated") double price_estimated,@Param("user")User user);


Ad findTopByOrderByAdIdDesc();
}