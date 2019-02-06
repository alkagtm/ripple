package com.ripple.trustline.service;



import com.ripple.trustline.data.TransferFunds;

import java.math.BigDecimal;

public interface TrustLineService {

	public void send(final TransferFunds transferFunds) throws Exception ;

	public void receive(final TransferFunds transferFunds) throws Exception;

	public BigDecimal getBalance();

}
