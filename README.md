# Ripple Java Technical Challenge
TrustLineService application using Spring Boot 

# Features
Built with Spring Boot

TrustLine keep track of debt between two parties

Client PostMan 

URL:-http:loalhost:XXXX/trustline/send (POST)
RequestObject Json
 {
   "receiver":"XYZ",
    "sender":"ABC",
    "amount":10
 }

# Running the app from CommanLine
Clone this repository:
    $ git clone https://github.com/alkagtm/ripple.git
    
    
Run "mvn clean package" to create the trustline-app-0.0.1-SNAPSHOT.jar.


Open two terminals

In Each Terminal execute the following command

In Terminal1 (Server1)
     $java -jar -Dlocal.server.port=8081 trustline-app-0.0.1-SNAPSHOT.jar --server.port=8080
     
In Terminal2 (Server2)
     $java -jar -Dlocal.server.port=8081 trustline-app-0.0.1-SNAPSHOT.jar --server.port=8081

Following message will be Displayed on Each Terminal 

Welcome to the Trustline

Trustline balance is: 0






     







