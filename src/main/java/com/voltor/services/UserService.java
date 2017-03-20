package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.voltor.bean.User;
import com.voltor.entity.TickEntity;
import com.voltor.entity.UserEntity;
import com.voltor.repositories.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TickService tickService;

	public User getUserByAuthName(String authName) {
		return getUser(userRepository.findOneByAuthName(authName));
	}
	
	public UserEntity getUserEntityByAuthName(String authName) {
		return userRepository.findOneByAuthName(authName);
	}

	public User getUserByAuthNameForLogin(String authName) {
		UserEntity entity = userRepository.findOneByAuthName(authName);
		if (entity == null) {
			return null;
		}

		return new User(entity.getAuthName(), entity.getAuthPassword(), entity.getRole());
	}

	public User save(User bean) {
		return getUser(userRepository.save(getUserEntity(bean)));
	}

	public void delete(User bean) {
		if (bean == null) {
			return;
		}
		userRepository.delete(userRepository.findOneById(bean.getId()));
	}

	public Collection<User> getAll() {
		return userRepository.findAll().stream().map(this::getUser).collect(Collectors.toList());
	}

	public User getUser(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		User user = new User();
		user.setAuthName(entity.getAuthName());
		user.setRole(entity.getRole());
		user.setId(entity.getId());
		user.setFirstName(entity.getFirstName());
		user.setLastName(entity.getLastName());
		user.setPhone(entity.getPhone());
		user.setEmail(entity.getEmail());
		user.setTick(entity.getTick());
		return user;
	}

	public UserEntity getUserEntity(User bean) {
		if (bean == null) {
			return null;
		}
		UserEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new UserEntity();
		} else {
			entity = userRepository.findOneById(bean.getId());
		}
		if (!Strings.isNullOrEmpty(bean.getAuthPassword())) {
			entity.setAuthPassword(new BCryptPasswordEncoder().encode(bean.getAuthPassword()));
		}
		if (!Strings.isNullOrEmpty(bean.getAuthName())) {
			entity.setAuthName(bean.getAuthName());
		}
		if (!Strings.isNullOrEmpty(bean.getFirstName())) {
			entity.setFirstName(bean.getFirstName());
		}
		if (!Strings.isNullOrEmpty(bean.getLastName())) {
			entity.setLastName(bean.getLastName());
		}
		entity.setPhone(bean.getPhone());
		entity.setEmail(bean.getEmail());

		entity.setRole(bean.getRole());
		
		TickEntity tickEntity = null;
//		if (bean.getTick() == null || bean.getTick().getId() == 0L) {
//			tickEntity = new TickEntity();
//			tickEntity.setValueUA(0.0);
//			tickEntity.setValueUSA(0.0);
//			tickEntity = tickService.add(tickEntity);
//		} else {
//			tickEntity = tickService.findOneById(bean.getTick().getId());
//			tickEntity.setValueUA(bean.getTick().getValueUA());
//			tickEntity.setValueUSA(bean.getTick().getValueUSA());
//		}
		entity.setTick( tickEntity  );

		return entity;
	}
	
	public Boolean isExistAuthName( String authName, Long id) {
		return userRepository.existAuthName(authName, id);
	}


}
