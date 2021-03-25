package tn.dari.spring.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class FournitureAdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    
	private LongFilter faID;
	private StringFilter userName;
	private StringFilter nameFa;
	private FloatFilter price;
	//boolean dipo;
	private StringFilter description;
	private StringFilter address;
	private StringFilter created;
	
	public FournitureAdCriteria(){}
	
	public FournitureAdCriteria(FournitureAdCriteria other) {
        this.faID = other.faID == null ? null : other.faID.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        //LocalDateFilter s = new LocalDateFilter();
        //s.equals("2020-02-03");
        //this.created = s ;
        this.nameFa = other.nameFa == null ? null : other.nameFa.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.created = other.created == null ? null : other.created.copy();
    }

    @Override
    public FournitureAdCriteria copy() {
        return new FournitureAdCriteria(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(

        		 faID,
        		userName,
        		 nameFa,
        		price,
        		description,
        		address,
        		created
        );
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FournitureAdCriteria that = (FournitureAdCriteria) o;
        return
            Objects.equals(faID, that.faID) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(nameFa, that.nameFa) &&
            Objects.equals(price, that.price) &&
            Objects.equals(description, that.description) &&
            Objects.equals(address, that.address) &&
            Objects.equals(created, that.created);
        }
	
	@Override
	public String toString() {
		return "FournitureAdCriteria [faID=" + faID + ", userName=" + userName + ", nameFa=" + nameFa + ", price="
				+ price + ", description=" + description + ", address=" + address + ", created=" + created + "]";
	}
    
    

}