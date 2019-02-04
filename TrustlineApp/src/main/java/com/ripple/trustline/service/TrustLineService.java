package com.ripple.trustline.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ripple.trustline.data.TransferFunds;

import java.math.BigDecimal;


public interface TrustLineService {
	

	public void send(TransferFunds transferFunds);
	public void receive(TransferFunds transferFunds);
	public BigDecimal  getBalance();

}
