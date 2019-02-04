package com.ripple.trustline.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripple.trustline.data.TransferFunds;
import com.ripple.trustline.service.TrustLineService;


@RestController
@RequestMapping("/trustline")
public class TrustLineController {
	
	@Autowired
	public TrustLineService trustlineService;
	@PostMapping("/send")
	public void send(@RequestBody TransferFunds transferFunds) {
		trustlineService.send(transferFunds);
	}
	
	@PostMapping("/receive")
	public void receive(@RequestBody TransferFunds transferFunds) {
		trustlineService.receive(transferFunds);
	}

}
