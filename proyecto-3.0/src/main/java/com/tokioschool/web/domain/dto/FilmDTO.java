package com.tokioschool.web.domain.dto;

import java.time.LocalDate;

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

public class FilmDTO {
	
	long id;
	String title;
	int year;
	int duration;
	String sipnosis;
	String poster;
	boolean migrate;
	LocalDate dateMigrate;
	
}
