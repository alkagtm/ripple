package com.ripple.trustline.service;



import com.ripple.trustline.data.TransferFunds;

import java.math.BigDecimal;

public interface TrustLineService {

	public void send(TransferFunds transferFunds);

	public void receive(TransferFunds transferFunds);

	public BigDecimal getBalance();

}
