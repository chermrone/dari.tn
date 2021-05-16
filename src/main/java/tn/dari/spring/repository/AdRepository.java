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
	////////////////////////////////////////////////////retrieve ad depending on SELL/RENT//////////////////////////////////////////////////////////
	@Query("SELECT u from Ad u WHERE u.typead=tn.dari.spring.enumeration.Typead.SELL")
	public List<Ad> retriveAdSell();
	@Query("SELECT u from Ad u WHERE u.typead=tn.dari.spring.enumeration.Typead.RENT")
	public List<Ad> retriveAdRent();

	////////////////////////////////////////////////count favorites for an ad//////////////////////////////////////////////////////////

	@Query(nativeQuery = true, value ="Select count(*) From user,ad,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And favorite=:id")
	public int retriveNumberOffavoritesForPremium(@Param("id") long id);
	
	
	
	////////////////////////////////////////////////Ads By critearia//////////////////////////////////////////////////////////


	@Query("SELECT u from Ad u WHERE price<=:price and city=:city and numbreOfRooms<=:rooms and typead=:typeAd and type=:typebat ")
public List<Ad> RetrieveByPriceAndCityAndNumbreOfRoomsAndTypeadAndType(double price, String city, int rooms, Typead typeAd,
		TypeBatiment typebat);

	
	@Query("SELECT u from Ad u WHERE price<=:price and city=:city and numbreOfRooms<=:rooms and type=:typebat ")
public List<Ad> RetrieveGroundByPriceAndCityAndNumbreOfRoomsAndTypeadAndType(double price, String city, int rooms,
		TypeBatiment typebat);
	////////////////////////////////////////////////Retrieve favorites for a user//////////////////////////////////////////////////////////


	@Query(nativeQuery = true, value ="Select user_favorite.favorite From ad,user,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And user.id_user=:id")
	public List<Long> retrievefavOwned(@Param("id") long id);
	
////////////////////////////////////////////////////get Fav city/////////////////////////////////////////////////////////
	/*@Query(nativeQuery = true, value ="Select user_favorite.favorite From ad,user,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And user.id_user=:id")
public String getCityFav(long id);
*/
	@Query(nativeQuery = true, value ="Select ad.city cont From ad,user,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And user.id_user=:id group by ad.city")
public String[] getMostFav(long id);
	////////////////////////////////////////////////Order Ad By price//////////////////////////////////////////////////////////


	@Query(nativeQuery = true, value="SELECT * from ad where 'RENT'=ad.typead  Order By ad.price LIMIT 5 ")
public List<Ad> RetrieveOrderedRENT();

	@Query(nativeQuery = true, value="SELECT * from ad where  "
			+ " 'SELL'=ad.typead Order By ad.price LIMIT 5  ")
public List<Ad> RetrieveOrderedSELL();
	////////////////////////////////////////////////delete a favorite for a user//////////////////////////////////////////////////////////


@Transactional
@Modifying
@Query(nativeQuery = true, value="delete from user_favorite where user_favorite.favorite=:id")
public void deletefavid(@Param("id") long id);






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
	@Query(nativeQuery = true, value ="Select :area*city.pricemetre From city "
			+ "Where "
			+ " city.name=Lower(:city) and Lower(:typeBatiment)='ground'")
	public double RetrievEstimatedPriceGround(@Param("area")double area,@Param("typeBatiment")String typeBatiment,@Param("city") String city);
	
	
	/////####appartment
	////////////////////////////::::Non Famous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(:builda*city.price_build , 2) from city "
			+ "Where city.name=Lower(:city) and Lower(:typeBatiment)='apartment'")
	public double RetrievEstimatedPriceAppartment(@Param("builda")double builda,@Param("typeBatiment")String typeBatiment,@Param("city") String city);
	///////Update table cities :column famous if top five =1
	@Transactional
	@Modifying
	@Query("Update City u set u.famous=true where u.name in (:listfamous)")
	public void UpdateCitiesFamous(@Param("listfamous") String[] topFive);
	/*////////////////////////////::::Famous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(:builda*city.price_build*1.75, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='apartment' ")
	public double RetrievEstimatedPriceFamousRegionAppartment(@Param("builda")double builda,@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	*/
	/////####house
	////////////////////////////::::Famous::::////////////////////////
/*	@Query(nativeQuery = true, value ="Select ROUND((:builda*city.price_build)*1.75+(:area-:builda)*city.pricemetre, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='house' ")
	public double RetrievEstimatedPriceFamousRegionHouse(@Param("builda")double builda,@Param("area")double area,
			@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	*/
	////////////////////////////::::NonFamous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND((:builda*city.price_build)+(:area-:builda)*city.pricemetre, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true "
			+ " And city.name=Lower(:city) and Lower(:typeBatiment)='house' ")
	public double RetrievEstimatedPriceHouse(@Param("builda")double builda,@Param("area")double area,
			@Param("typeBatiment")String typeBatiment,@Param("city") String city,@Param("user")User user);
	
	/////####RENT
	
	////////////////////////////::::NonFamous::::////////////////////////

	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true  "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceOnlyRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	
	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.7, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true"
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceMultiRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	////////////////////////////::::Famous::::////////////////////////

	/*@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.8, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceFamousRegionOnlyRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);
	
	@Query(nativeQuery = true, value ="Select ROUND(price_rent*:nber_rooms*0.5*1.1, 2) From user,city "
			+ "Where user.id_user=:#{#user.idUser} and user.user_state=true and city.famous=true "
			+ " And city.name=Lower(:city) ")
	public double RetrievEstimatedRentPriceFamousRegionMultiRoom(@Param("nber_rooms") int nber_rooms,@Param("city") String city,@Param("user")User user);

*/



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
		+ " And city.name=Lower(:city)  and :price_entered-:price_estimated<=0 and "
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

@Transactional
@Modifying
@Query("delete from Ad a  where a.adId =:id")
public void deleteAd(@Param("id") long id);


@Transactional
@Modifying
@Query("delete from FilesAd f  where f.ad=:ad")
public void deleteImgAd(@Param("ad") Ad ad);


@Transactional
@Modifying
@Query("delete from Claim f  where f.ad=:ad")
public void deleteClaim(@Param("ad") Ad ad);


@Transactional
@Modifying
@Query("delete from Wishlist f  where f.ad=:ad")
public void deleteWishlist(@Param("ad") Ad ad);
 

@Transactional
@Modifying
@Query("delete from Review f  where f.ad=:ad")
public void deleteReview(@Param("ad") Ad ad);




}