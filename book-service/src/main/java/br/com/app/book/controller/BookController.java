package br.com.app.book.controller;

import br.com.app.book.dto.Exchange;
import br.com.app.book.enviroment.InstanceInfoService;
import br.com.app.book.models.Book;
import br.com.app.book.proxys.ExchangeProxy;
import br.com.app.book.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book", description = "Book Management")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private InstanceInfoService informationService;

    @Autowired
    private BookRepository repository;

    @Autowired
    private ExchangeProxy proxy;

    @Operation(summary = "Find a book by id and currency")
    @GetMapping(value = "/{id}/{currency}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ){
        var port = informationService.retriveServerPort();

        var book = repository.findById(id).orElseThrow();

        Exchange exchange = proxy.getExchange(book.getPrice(), "USD", currency);

        book.setEnvironment(port + " FEIGN");
        book.setPrice(exchange.getConvertedValue());
        book.setCurrency(currency);
        return book;
    }
}
