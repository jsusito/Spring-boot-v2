package com.tokioschool.spring.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.ReviewDTO;
import com.tokioschool.spring.service.ReviewService;
import com.tokioschool.spring.service.UserService;
import com.tokioschool.spring.util.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j

@Tag(name ="Reviews" , description ="Reviews the film by idUser and idFilm ")
public class ControllerReview {

	private final ReviewService reviewService;
	private final UserService userService;
	
	@Operation(summary ="Get Reviews by film Primary Key ")
	@GetMapping("/review/{id}")
	private ResponseEntity<Set<ReviewDTO>> getReview(@PathVariable Long id){
		
		Film film = Film.builder().id(id).build();
		log.info("Consultando BD review");
		
		return ResponseEntity.ok( reviewService.findByFilm(film) );
	}
	
	@Operation(summary ="Save film`s review")
	@PostMapping("/new-review")
	public ResponseEntity<Response> newReview(@RequestBody ReviewDTO reviewDTO){
		 
		Optional<Review> maybeReview = reviewService.findByUserAndFilm(reviewDTO) ;
		
		if(maybeReview.isPresent())
			 throw new ResponseStatusException(HttpStatus.FOUND ,"Ya existe la critica");
		
		reviewService.save(reviewDTO);
		
		Response response = new Response(Response.NO_ERROR, Response.NO_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	@Operation(summary ="get all reviews of a user")
	@GetMapping("/user/{id}/review")
	ResponseEntity<Set<ReviewDTO>> getReviewByUser(@PathVariable Long id){
		
		Optional<User> userOpt = userService.findById(id);
		if(userOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"No existe el usuario");
		
		log.info("Devolviendo consulta review");
		return ResponseEntity.ok( reviewService.findByUser(userOpt.get()) );
		
	}

	
}
