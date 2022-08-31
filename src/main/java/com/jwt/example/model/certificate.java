package com.jwt.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class certificate {
	
	@Id
	int id;
	
	private String htmlconten;
	
	private String htmltype;

}
