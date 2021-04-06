package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.SubscriptionOrdred;

public interface UISubscriptionOrderService {
	public SubscriptionOrdred AddSubscriptionorder(SubscriptionOrdred s,Long id);
	public SubscriptionOrdred AddPremiumSubscriptionorder(SubscriptionOrdred s,Long id);
	public SubscriptionOrdred UpdateSubscriptionorder(SubscriptionOrdred s);
	public void deleteSubscriptionOrder(Long id);
	public List<SubscriptionOrdred> GetAll();
	public SubscriptionOrdred GetSubscriptionorder(Long id);
	public List<SubscriptionOrdred> GetByEnable(boolean enable);
	public List<SubscriptionOrdred> GetByUser(Long id);
}
