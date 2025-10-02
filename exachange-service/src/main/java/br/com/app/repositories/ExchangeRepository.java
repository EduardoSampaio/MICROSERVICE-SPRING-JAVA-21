package br.com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.app.models.Exchange;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

	Exchange findByFromAndTo(String from, String to);
}
