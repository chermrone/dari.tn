package tn.dari.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.User;
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

	@Query("SELECT u from Ad u JOIN  u.us uss "+ "join uss.roles r WHERE r.name=:role")
	public List<Ad> retriveAdDependingOnRole(@Param("role") Usertype role);

//	@Query("SELECT count(u.Favorite) from User u join u.roles rr "
//			+ "join u.ads aa WHERE rr.name=tn.dari.spring.enumeration.Usertype.PREMIUM and  aa.adId=:id")

	@Query(nativeQuery = true, value ="Select count(*) From user,ad,user_favorite "
			+ "Where user.id_user=user_favorite.user_id_user "
			+ "And ad.ad_id=user_favorite.favorite And favorite=:id")
	public int retriveNumberOffavoritesForPremium(@Param("id") long id);
	@Query("SELECT u from Ad u JOIN  u.us uss "+ "join uss.roles r "
			+ "WHERE uss.userState=false  and uss.banDate between :mindate  and :maxdate  "
			+ "and r.id=:role")
	public List<Ad> retrieveAdByBannedUser(@Param("role") Long role,@Param("maxdate") Date maxdays,@Param("mindate") Date mindays);
	

	@Query("select count(u) > 0 from Ad  u join u.us uss where uss.userState=false and  u.adId = :id")
public boolean CheckAdBanned(@Param("id")long id);
	@Query("SELECT count(u)>0 from User u "+ 
"join u.roles r WHERE u=:user and r.name=tn.dari.spring.enumeration.Usertype.ADMIN")
	public boolean CheckExistingRoleADMINforConsulterOfAd(@Param("user") User user);

}
