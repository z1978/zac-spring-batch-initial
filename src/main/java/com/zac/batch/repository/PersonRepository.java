package com.zac.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.zac.batch.entity.Person;

@Transactional
public interface PersonRepository extends JpaRepository<Person, Integer>  {
	List<Person> findById(int id);
}
