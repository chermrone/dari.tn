package tn.dari.spring.entity;


public class CardInfo {
    private String number;
    private String exp_month;
    private String exp_year;
    private String cvc;
    
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getExp_month() {
        return exp_month;
    }
    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }
    public String getExp_year() {
        return exp_year;
    }
    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }
    public String getCvc() {
        return cvc;
    }
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    
}
