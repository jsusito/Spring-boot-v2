package com.tokioschool.web.util;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.tokioschool.web.domain.Review;
import com.tokioschool.web.domain.dto.UserDTO;
import com.tokioschool.web.jwt.JwtResponse;

import jakarta.servlet.http.HttpSession;


public class HelperRest {
	
	private HelperRest() {}
	
	//Metodo para hacer la cabecera
	public static HttpHeaders getHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return header;
		}
	
			
	/**
	 * @param httpSession
	 * @param urlApiRest
	 * @return Return a authorization`s header is already formed with Bearer + token
	 * @throws JsonProcessingException
	 */
	
	public static HttpHeaders getTokenAuthorizacionHeader(String username, String password, HttpSession httpSession, String urlApiRest) throws JsonProcessingException {
				
		RestTemplate restTemplate = new RestTemplate();
		Logger logger = LoggerFactory.getLogger(HelperRest.class);
		Date dateExpired = null;
		boolean expiredToken = false;
		
		String token = (String) httpSession.getAttribute("Bearer");
		JWSObject jwsObject;
		
		if(token != null) {
			
			try {
				jwsObject = JWSObject.parse(token);
				JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
				dateExpired = claimsSet.getExpirationTime();
				expiredToken = dateExpired.getTime() < new Date().getTime() - 5000L;

			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		
		if(token == null || expiredToken) {
			UserDTO userDTO = new UserDTO(username, password);
			logger.info("No hay token procedemos a solicitar uno");
			
			if(expiredToken)
				logger.info("Token caducado, procedemos a renovarlo");
			
			HttpEntity<String> httpEntity = new HttpEntity<>(HelperRest.getBody(userDTO), HelperRest.getHeaders());
			ResponseEntity<JwtResponse> authenticationResponse = restTemplate.exchange(urlApiRest, HttpMethod.POST, httpEntity, JwtResponse.class );
			
			logger.info("Token Generado");
			token = authenticationResponse.getBody().getToken();
			httpSession.setAttribute("Bearer", token);
		}
		
		//Creamos la cabecera
		HttpHeaders header = HelperRest.getHeaders();
		header.set("Authorization", "Bearer " + token );
		 
		return header;
		}
	
	public static String getBody(UserDTO user) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(user);
	}

	public static String getBody(Review review) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(review);
	}

}
