package br.com.app.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.app.enviroment.InstanceInfoService;
import br.com.app.models.Exchange;

@RestController
@RequestMapping("/exchange-service")
public class ExchangeController {

	@Autowired
	private InstanceInfoService instanceInfoService;

	@Autowired
	private ExchangeRepository repository;
	
	@GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Exchange getExchange(@PathVariable String amount, 
								@PathVariable String from, 
								@PathVariable String to) {

		Exchange exchange = repository.findByFromAndTo(from, to);
		if(exchange == null) {
			throw new RuntimeException("Currency not found");
		}
		BigDecimal  convertionFactor = exchange.getConvertionFactor();
		BigDecimal converttedValue = convertionFactor.multiply(amount);
		exchange.setConverttedValue(converttedValue);
		exchange.setPort(instanceInfoService.retriveServerPort());
		exchange.setEnvironment("PORT: " + instanceInfoService.getEnvironment());
		return exchange;
	}
}
