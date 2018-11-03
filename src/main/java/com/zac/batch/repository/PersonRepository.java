package com.zac.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zac.batch.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>  {
	Person findById(int id);
}
