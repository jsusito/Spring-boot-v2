package com.tokioschool.web.domain;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity(name = "films")

public class Film {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(nullable = false)
	String title;
	
	int year;
	int duration;
	
	@Column(nullable = false)
	String sipnosis;
	
	String poster;
	boolean migrate;
	LocalDate dateMigrate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@OneToMany(mappedBy = "film")
	Set<Review> reviews;
	
	@OneToMany(mappedBy = "film")
	Set<Score> scores;
	
	@ManyToOne
	@JoinColumn(name = "director_id")
	Person filmDirector;
	
	@ManyToOne
	@JoinColumn(name = "photographer_id")
	Person filmPhotographer;
	
	@ManyToMany
	@JoinTable(name="musicians_films", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> musicians;
	
	@ManyToMany
	@JoinTable(name="actors_films", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> actors;
	
	@ManyToMany
	@JoinTable(name="screenwriter_film", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> screenWriters;
	
	public String toString() {
		return title + " " + user.getId();
	}
}
