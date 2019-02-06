package com.ripple.trustline.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ripple.trustline.data.TransferErroResponse;
import com.ripple.trustline.data.TransferFunds;
import com.ripple.trustline.service.TrustLineService;

@RestController
@RequestMapping("/trustline")
public class TrustLineController {

	@Autowired
	public TrustLineService trustlineService;

	@PostMapping("/send")
	public ResponseEntity<String> send(final @RequestBody TransferFunds transferFunds) throws Exception {
		trustlineService.send(transferFunds);
		return new ResponseEntity("Successfully Transferred",HttpStatus.OK);
	}

	@PostMapping("/receive")
	public ResponseEntity<String> receive(final @RequestBody TransferFunds transferFunds) throws Exception {
		trustlineService.receive(transferFunds);
		return new ResponseEntity("Successfully Transferred",HttpStatus.OK);
		
	}

}
