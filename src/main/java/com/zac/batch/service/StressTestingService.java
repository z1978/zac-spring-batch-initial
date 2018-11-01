package com.zac.batch.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zac.batch.entity.StressTesting;
import com.zac.batch.repository.StressTestingRepository;

@Service
public class StressTestingService {
	final static Logger LOGGER = LoggerFactory.getLogger(StressTestingService.class);

	@Autowired
	private StressTestingRepository stressTestingRepository;

	@Transactional(readOnly = true)
	public List<StressTesting> getAll() {
		return stressTestingRepository.findAll();

	}

	@Transactional
	public void save(StressTesting stressTesting) {
		stressTestingRepository.save(stressTesting);
	}

	// 外部からこのメソッドを呼び出した場合、saveで例外が発生してもロールバックされない
	public void save2(StressTesting entity) {
		save(entity);
	}

	@Transactional
	public StressTesting saveAndFlush(StressTesting stressTesting) {
		if (stressTesting != null) {
			stressTesting = stressTestingRepository.saveAndFlush(stressTesting);
		}

		return stressTesting;
	}

	@Transactional
	public void deleteAll() {
		stressTestingRepository.deleteAll();
	}

//	public StressTestingService(StressTestingRepository stressTestingRepository) {
//		this.stressTestingRepository = stressTestingRepository;
//	}
//
//	public List<StressTesting> findAll() {
//		return stressTestingRepository.findAll();
//	}
//
//	//@Modifying
//	public void deleteAll() {
//		stressTestingRepository.deleteAll();
//	}
//
    //@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Transactional
	public void createOne(StressTesting stressTesting) {
		stressTestingRepository.save(stressTesting);
	}
//
//    //@Transactional(readOnly = false)
//	public void deleteById(Integer id) {
//		stressTestingRepository.deleteById(id);
//	}
//    
//	public void saveOne(StressTesting stressTesting) {
//		stressTestingRepository.saveAndFlush(stressTesting);
//	}

}
