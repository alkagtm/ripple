package com.ripple.trustline.trackingApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;

import com.ripple.trustline.data.TransferFunds;
import com.ripple.trustline.service.TrustLineService;

@RunWith(SpringRunner.class)
public class TrustLineServiceImplTests {
 
   
    @Mock
    private TrustLineService trustlineService;

 
    // write test cases here
    
	@Test
    public void send_thenValueShouldbeDebited() throws Exception {
    	
    	when(trustlineService.getBalance()).thenReturn(new BigDecimal(-20));
    	TransferFunds funds = new TransferFunds();
    	funds.setReceiver("receiver");
    	funds.setSender("sender");
    	funds.setAmount(new BigDecimal(20));
    	trustlineService.send(funds);
    	assertTrue(trustlineService.getBalance().doubleValue()==new BigDecimal(-20).doubleValue());
     }
    
    @Test
    public void receiver_thenValueShouldbeCredited()throws Exception {
    	
    	when(trustlineService.getBalance()).thenReturn(new BigDecimal(+20));
    	TransferFunds funds = new TransferFunds();
    	funds.setReceiver("receiver");
    	funds.setSender("sender");
    	funds.setAmount(new BigDecimal(20));
    	trustlineService.receive(funds);
    	assertTrue(trustlineService.getBalance().doubleValue()==new BigDecimal(20).doubleValue());
     }


}