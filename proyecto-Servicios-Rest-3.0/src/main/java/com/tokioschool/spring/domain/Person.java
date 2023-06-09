package com.tokioschool.spring.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "persons")
public class Person {
	
	public static enum TypePerson {GUIONISTA, MUSICO, FOTOGRAFO, ACTOR, DIRECTOR}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String surname;
	@Column(nullable = false)
	TypePerson typePerson;
	
	@OneToMany(mappedBy = "filmDirector")
	Set<Film> filmsDirector;
	
	@OneToMany(mappedBy = "filmPhotographer")
	Set<Film> filmsPhotographer;
	
	@ManyToMany
	@JoinTable(name="musicians_films", joinColumns = @JoinColumn(name="person_id"), inverseJoinColumns = @JoinColumn(name = "film_id"))
	private Set<Film> filmsMusicians;
	
	@ManyToMany
	@JoinTable(name="actors_films", joinColumns = @JoinColumn(name="person_id"), inverseJoinColumns = @JoinColumn(name = "film_id"))
	private Set<Film> filmsActor;
	
	@ManyToMany
	@JoinTable(name="screenwriter_film", joinColumns = @JoinColumn(name="person_id"), inverseJoinColumns = @JoinColumn(name = "film_id"))
	private Set<Film> filmsScreenwriters;
	
	@Override
	public String toString() {
		return name + " " + surname;
	}
}
