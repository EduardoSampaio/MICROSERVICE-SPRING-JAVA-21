package br.com.app.controller;

import java.math.BigDecimal;

import br.com.app.repositories.ExchangeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.com.app.enviroment.InstanceInfoService;
import br.com.app.models.Exchange;

@Tag(name = "Exchange", description = "exchange service")
@RestController
@RequestMapping("/exchange-service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExchangeController {

	@Autowired
	private InstanceInfoService instanceInfoService;

	@Autowired
	private ExchangeRepository repository;

	@Operation(summary = "Find a exchange by id and currency")
	@GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Exchange getExchange(@PathVariable BigDecimal amount,
								@PathVariable String from, 
								@PathVariable String to) {

		Exchange exchange = repository.findByFromAndTo(from, to);
		if(exchange == null) {
			throw new RuntimeException("Currency not found");
		}
		BigDecimal convertionFactor = exchange.getConversionFactor();
		BigDecimal converttedValue = convertionFactor.multiply(amount);
		exchange.setConvertedValue(converttedValue);
		exchange.setEnvironment("PORT: " + instanceInfoService.retriveServerPort());
		return exchange;
	}
}
