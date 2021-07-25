This POC examplifies to modify Request and Response objects through Interceptors

1. For modifying request object, we are Encrypting our request object before sending.<br>
For modifying our response object, we are decrypting the received response. The decrypted response is then used to prepare the <u>new response object</u> which will be returned.


2. Prepared two Spring beans of RestTemplate <br>
a) <U>RegularRestTemplate Bean</U>: This will be used in project where Interceptor is not required while aking REST calls<br>
   b) <U>InterceptedRestTemple Bean</U>: To be used for making REST calls, which are to be intercepted for change in Request Response objects 
   
   
3. Custom Interceptor is also perpared, where the request response objects are modified<br>
Encryption applied on request object before sending as payload<br>
   Decryption applied on received response. <br>
   A new response object prepared using decrypted response and sent to the service making call.