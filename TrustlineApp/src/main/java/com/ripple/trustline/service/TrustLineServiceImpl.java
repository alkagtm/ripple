package com.ripple.trustline.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.ripple.trustline.data.TransferFunds;
import java.math.BigDecimal;

/**
 * @author alkagautam
 *
 */
@Service
public class TrustLineServiceImpl implements TrustLineService {

	private static final Logger log = LogManager.getLogger(TrustLineService.class);

	@Value("${reciever1.port}")
	private int reciever1port;

	@Value("${reciever2.port}")
	private int reciever2port;

	@LocalServerPort
	private int localport;

	@Value("${host}")
	private String host;

	private static Map<String, BigDecimal> balanceSheet = Collections
			.synchronizedMap(new HashMap<String, BigDecimal>());
	private static final String BALANCE = "balance";
	
	private final ReentrantLock lock = new ReentrantLock();

	/*
	 * @param TransferFunds 1) updates the BalanceSheet by subtracting form the
	 * existing balance 2) Sends the Amount to the Receiver
	 */
	public void send(TransferFunds transferFunds) {
		try {

			updateSendersBalanceSheet(transferFunds);
			log.info("You sent " + transferFunds.amount);
			log.info("Trustline balance is: " + balanceSheet.get(BALANCE));
			int targetPort = (localport == reciever1port) ? reciever2port : reciever1port;
			UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(host).port(targetPort)
					.path("/trustline/receive").build();
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForObject(uriComponents.toUriString(), transferFunds, TransferFunds.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * @param TransferFunds 1) updates the BalanceSheet by adding to the existing
	 * balance
	 * 
	 */
	public void receive(TransferFunds transferFunds) {
		try {
			if (transferFunds != null) {
				updateReceiversBalanceSheet(transferFunds);
				log.info("You were paid " + transferFunds.amount);
				log.info("Trustline balance is: " + balanceSheet.get(BALANCE));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * updatingSenderBalmceSheet
	 */
	private void updateSendersBalanceSheet(TransferFunds transferFunds) {

		if (transferFunds != null) {
			
				if (!(balanceSheet.isEmpty())) {
					lock.lock();
					BigDecimal currentAmount = balanceSheet.get(BALANCE);
					BigDecimal latestAmount = currentAmount.subtract(transferFunds.amount);
					balanceSheet.put(BALANCE, latestAmount);
					lock.unlock();
				} else {
					lock.lock();
					balanceSheet.put(BALANCE, new BigDecimal(0).subtract(transferFunds.amount));
					lock.unlock();
				}
		}
	}

	/*
	 * updatingReceiverBalmceSheet
	 */
	private void updateReceiversBalanceSheet(TransferFunds transferFunds) {
		if (transferFunds != null) {
				if (!(balanceSheet.isEmpty())) {
					lock.lock();
					BigDecimal currentAmount = balanceSheet.get(BALANCE);
					BigDecimal latestAmount = currentAmount.add(transferFunds.amount);
					balanceSheet.put(BALANCE, latestAmount);
					lock.unlock();
				} else {
					balanceSheet.put(BALANCE, transferFunds.amount);
				}
				
				
		}
	}

	public BigDecimal getBalance() {
		return balanceSheet.get(BALANCE);
	}

}
