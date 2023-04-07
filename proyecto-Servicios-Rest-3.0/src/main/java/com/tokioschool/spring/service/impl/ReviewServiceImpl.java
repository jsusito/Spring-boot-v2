package com.tokioschool.spring.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.ReviewDTO;
import com.tokioschool.spring.domain.repository.ReviewRepository;
import com.tokioschool.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
		
	@Override
	public Optional<Review> findByUserAndFilm(ReviewDTO reviewDTO) {

		Review review = Review.convertToReview(reviewDTO);
		return reviewRepository.findByUserAndFilm(review.getUser(), review.getFilm());
	}

	@Override
	public Set<ReviewDTO> findByFilm(Film film) {
		
		return reviewRepository.findByFilm(film)
				.stream()
				.map(ReviewDTO::convertToDTO)
				.collect(Collectors.toSet());
						
	}

	@Override
	public void save(ReviewDTO reviewDTO) {
		
		Review review = Review.convertToReview(reviewDTO);
		reviewRepository.save(review);
	}

	@Override
	public Set<ReviewDTO> findByUser(User user) {
		
		return reviewRepository.findByUser(user)
				.stream()
				.map(ReviewDTO::convertToDTO)
				.collect(Collectors.toSet());
	}

}
