package com.service.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "person")
public class PersonDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private int personId;
	private String firstName;
	private String lastName;
	private int age;
	private String favouriteColour;

	@ManyToMany(cascade = { CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinTable(name = "person_hobby", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "hobby_id"))
	private Set<Hobby> hobbys;

	public PersonDetails(String firstName, String lastName, int age, String favouriteColour, Set<Hobby> hobbys) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.favouriteColour = favouriteColour;
		this.hobbys = hobbys;
	}
}
