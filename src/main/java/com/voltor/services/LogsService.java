package com.voltor.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.LogsEntity;
import com.voltor.repositories.LogsRepository;

@Component
public class LogsService {

	@Autowired
	private LogsRepository repository;

	public void save(String instance, String description) {
		LogsEntity logsEntity = new LogsEntity();
		logsEntity.setDate( new Date() );
		logsEntity.setUserId( SecurityService.getCurrentUser().getId() );
		logsEntity.setInstance(instance);
		logsEntity.setDescription(description);
		repository.save( logsEntity );
	}


}
