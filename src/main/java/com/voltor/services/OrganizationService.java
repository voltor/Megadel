package com.voltor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.OrganizationEntity;
import com.voltor.repositories.OrganizationRepository;

@Component
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository; 
	
	public OrganizationEntity getOrganization(){
		return organizationRepository.findOneById((long) 1);
	}
	

}
