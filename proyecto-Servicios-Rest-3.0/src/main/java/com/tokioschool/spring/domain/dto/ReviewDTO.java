package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import com.tokioschool.spring.domain.Review;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
		@Schema(required = true)
		String title;
		@Schema(required = true)
		String textReview;
		@Schema(required = true)
		LocalDate date;
		@Schema(required = true)
		String UserUsername;
		@Schema(required = true)
		long userId;
		@Schema(required = true)
		long filmId;
	
	
	public static ReviewDTO convertToDTO(Review review) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper.map(review, ReviewDTO.class);
	}
}
