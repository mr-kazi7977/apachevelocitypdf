package com.jwt.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jwt.example.model.certificate;

public interface cerRepo extends JpaRepository<certificate, Integer> {
	
	@Query(value = "select htmlconten from certificate where htmltype='s'", 
			  nativeQuery = true)
	String	 findcontent();

}
