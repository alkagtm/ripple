package com.ripple.trustline.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.ripple.trustline.data.TransferFunds;
import java.math.BigDecimal;
import java.net.URI;

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

	private static Map<String, BigDecimal> balanceSheet;

	private static final String BALANCE = "balance";

	private final ReentrantLock lock = new ReentrantLock();

	public TrustLineServiceImpl() {
		balanceSheet = Collections.synchronizedMap(new HashMap<String, BigDecimal>());
		balanceSheet.put(BALANCE, new BigDecimal(0));
	}

	/*
	 * @param TransferFunds 1) Sends the Amount to the Receiver 2) if no exception
	 * then update the Sender BalanceSheet by subtracting form the existing balance
	 */
	public void send(final TransferFunds transferFunds) throws Exception {
		try {
			int targetPort = (localport == reciever1port) ? reciever2port : reciever1port;

			URI uri = UriComponentsBuilder.newInstance().scheme("http").host(host).port(targetPort)
					.path("/trustline/receive").build().toUri();

			RequestEntity<TransferFunds> request = RequestEntity.put(uri).contentType(MediaType.APPLICATION_JSON)
					.body(transferFunds);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> exchange = restTemplate.exchange(request, String.class);
		
			if (exchange.getStatusCode() == HttpStatus.OK) {
				updateSendersBalanceSheet(transferFunds);
				log.info("You sent " + transferFunds.amount);
				log.info("Trustline balance is: " + balanceSheet.get(BALANCE));
			} else {
				log.info("TransferService Failed");
			}

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/*
	 * @param TransferFunds 1) updates the BalanceSheet by adding to the existing
	 * balance and return the response;
	 * 
	 */
	public void receive(final TransferFunds transferFunds) throws Exception {
		try {
			if (transferFunds != null) {
				updateReceiversBalanceSheet(transferFunds);
				log.info("You were paid " + transferFunds.amount);
				log.info("Trustline balance is: " + balanceSheet.get(BALANCE));
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/*
	 * updatingSenderBalmceSheet
	 */
	private void updateSendersBalanceSheet(final TransferFunds transferFunds) {

		if (transferFunds != null) {
			lock.lock();
			BigDecimal currentAmount = balanceSheet.get(BALANCE);
			BigDecimal latestAmount = currentAmount.subtract(transferFunds.amount);
			balanceSheet.put(BALANCE, latestAmount);
			lock.unlock();
		}
	}

	/*
	 * updatingReceiverBalmceSheet
	 */
	private void updateReceiversBalanceSheet(final TransferFunds transferFunds) {
		if (transferFunds != null) {
			lock.lock();
			BigDecimal currentAmount = balanceSheet.get(BALANCE);
			BigDecimal latestAmount = currentAmount.add(transferFunds.amount);
			balanceSheet.put(BALANCE, latestAmount);
			lock.unlock();
		}
	}

	public BigDecimal getBalance() {
		return balanceSheet.get(BALANCE);
	}

}
