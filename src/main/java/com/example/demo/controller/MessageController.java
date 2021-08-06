package com.example.demo.controller;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAutoConfiguration
@RestController
//@PropertySource("classpath:application.properties")
public class MessageController {
	
	@Value("${EZTEXT.userName}")
	private String userName;
	
	@Value("${EZTEXT.password}")
	private String password;
	
	@Value("${EZTEXT.url}")
	private String url;
	
	@GetMapping("/message/{msg}")
	public String message(@PathVariable String msg) {
		return "hello " + msg;
	}
	
	@PostMapping("/message")
	public String message(@RequestBody Message message) {
		System.out.println(message);
		System.out.println("url ="+ url + " userName = "+ userName+ " password = "+ password);
		//String response = httpsCallToEztext(message);
		//System.out.println("response = "+ response);
		return message.getMessage();
	}
	
	private String callEzText(Message message) {
		
        
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = createHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(getBodyInString(message),headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
		if(HttpStatus.OK.equals(result.getStatusCode().toString())){
			System.out.println("getting 200 ");
		}
		return result.toString();
	}
	
	
	private String httpsCallToEztext(Message message) {
		String result = "success";
		String eztextUrl = "https://a.eztexting.com/v1/messages";
		String userName = "Likitha.abbareddy@gmail.com";
		String password = "likitha";
		String postBody = getBodyInString(message);
	
		try {
			URL url = new URL(eztextUrl);

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String host, SSLSession session) {
					return true;
				}
			});
			  HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		      connection.setRequestMethod("POST");
		      connection.setDoInput(true);
		      connection.setDoOutput(true);
		      String auth = userName + ":" + password;
		      byte[] encodedAuth = Base64.getEncoder().encode( 
		      auth.getBytes(Charset.forName("US-ASCII")) );
              String authHeader = "Basic " + new String( encodedAuth );
		      connection.setRequestProperty("Authorization", authHeader );
		      connection.setRequestProperty("Content-Type", "application/json");
		      connection.setRequestProperty("Accept", "application/json");
		      connection.setRequestProperty("Content-Length", String.valueOf(postBody.length()));
		      OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		      wr.write(postBody);
		      wr.flush();
		      int HttpResult = connection.getResponseCode();
		      System.out.println("HttpResult = "+ HttpResult);
		      if (HttpResult != HttpsURLConnection.HTTP_CREATED) {
		    	  result = "Errror";
		      }
		      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Errror";
		}
		return result;
	}
	
	String getBodyInString(Message message) {
		ObjectMapper objectMapper = new ObjectMapper();
		String messageAsString = null;
		try {
			messageAsString = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(messageAsString);
		return messageAsString;
	}
	
	HttpHeaders createHeaders(){
		   return new HttpHeaders() {{
		         String auth = userName + ":" + password;
		         byte[] encodedAuth = Base64.getEncoder().encode( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		}
}
