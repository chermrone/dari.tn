package tn.dari.spring.entity;

import java.time.Instant;
//import java.time.LocalDate;
//import java.util.Date;
import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FournitureAd.class)
public abstract class FournitureAd_ {

    public static volatile SingularAttribute<FournitureAd, Long> faID;
    public static volatile SingularAttribute<FournitureAd, String> userName;
    public static volatile SingularAttribute<FournitureAd, String> nameFa;
    public static volatile SingularAttribute<FournitureAd, Float> price;
    public static volatile SingularAttribute<FournitureAd, String> description;
    public static volatile SingularAttribute<FournitureAd, String> address;
    public static volatile SingularAttribute<FournitureAd, String> created;
    public static volatile SingularAttribute<FournitureAd, Boolean> available;
    

    public static final String FAID = "faID";
    public static final String USERNAME = "userName";
    public static final String NAMEFA = "nameFa";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String ADDRESS = "address";
    public static final String CREATED = "created";
    public static final String AVAILABLE = "available";
}
