package com.tokioschool.web.domain;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
	
		String title;
		String textReview;
		LocalDate date;
		String userUsername;
		long userId;
		long filmId;
	

		public static ReviewDTO convertDTO(Review review) {
			
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(review, ReviewDTO.class);
		}
	
}
