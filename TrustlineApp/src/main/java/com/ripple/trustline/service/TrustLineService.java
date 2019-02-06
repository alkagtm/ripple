package com.ripple.trustline.service;



import com.ripple.trustline.data.TransferFunds;

import java.math.BigDecimal;

public interface TrustLineService {

	public boolean send(final TransferFunds transferFunds) throws Exception ;

	public boolean receive(final TransferFunds transferFunds) throws Exception;

	public BigDecimal getBalance();

}
