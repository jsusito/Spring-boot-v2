package com.tokioschool.spring.service;

import java.util.Optional;
import java.util.Set;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.ReviewDTO;

public interface ReviewService {
	Optional<Review> findByUserAndFilm(ReviewDTO reviewDTO);
	Set<ReviewDTO> findByFilm(Film film);
	Set<ReviewDTO> findByUser(User user);
	void save(ReviewDTO reviewDTO);
}
