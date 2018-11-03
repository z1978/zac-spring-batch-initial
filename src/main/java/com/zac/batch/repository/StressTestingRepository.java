package com.zac.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zac.batch.entity.StressTesting;

public interface StressTestingRepository extends JpaRepository<StressTesting, Integer>  {
	List<StressTesting> findById(int id);
}
