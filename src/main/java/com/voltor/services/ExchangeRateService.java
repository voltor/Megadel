package com.voltor.services;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.ExchangeRate;
import com.voltor.entity.ExchangeRateEntity;
import com.voltor.repositories.ExchangeRateRepository;

@Component
public class ExchangeRateService {
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	public static double CURRENT_EXCHAGE_RATE_VALUE; 
	public static ExchangeRate CURRENT_EXCHAGE_RATE; 
	public Collection<ExchangeRate> getAll() {
		return exchangeRateRepository.findAllByOrderByDateTimeDesc().stream().map(this::getExchangeRate).collect(Collectors.toList());
	}

	public ExchangeRate save(ExchangeRate bean) {
		return getExchangeRate(exchangeRateRepository.save(getExchangeRateEntity(bean)));
	}

	public ExchangeRate getExchangeRate(ExchangeRateEntity entity) {
		if (entity == null) {
			return null;
		}

		ExchangeRate bean = new ExchangeRate();
 
		bean.setId(entity.getId());
		bean.setDateTime(entity.getDateTime()); 
		bean.setCurrency(entity.getCurrency());
		return bean;
	}

	public ExchangeRateEntity getExchangeRateEntity(ExchangeRate bean) {
		if (bean == null) {
			return null;
		}
		ExchangeRateEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new ExchangeRateEntity(); 
			entity.setDateTime( new Date() ); 
		} else {
			entity = exchangeRateRepository.findOneById(bean.getId()); 
			entity.setDateTime(bean.getDateTime()); 
		}
		entity.setId(bean.getId());
		entity.setCurrency(bean.getCurrency());

		return entity;
	}

	public ExchangeRate getCurrentExchangeRate() {
		ExchangeRate result = getExchangeRate( exchangeRateRepository.findTop1ByOrderByDateTimeDesc() ) ;
		if(result==null){
			result = new ExchangeRate();
			result.setCurrency(0.0);
		}
		CURRENT_EXCHAGE_RATE = result;
		CURRENT_EXCHAGE_RATE_VALUE = result.getCurrency();
		return result;
	} 
	
	public static Double getPriseInUA( double price ){
		double result = price * CURRENT_EXCHAGE_RATE_VALUE *100;
		result = ( (int) result );
		result /= 100;
		double t = ( (int) result );
		double v = 0.0;
		if( (result-t) > 0.0 ){
			if( (result-t) <= 0.50 ){
				v = 0.5;
			}
			if( (result-t) > 0.50 ){
				t++;
			}
		}
		
		return t + v ;
	}

}
