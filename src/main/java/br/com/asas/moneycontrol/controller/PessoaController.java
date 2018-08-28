package br.com.asas.moneycontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.asas.moneycontrol.entity.Pessoa;
import br.com.asas.moneycontrol.exception.PessoaException;
import br.com.asas.moneycontrol.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> all() {
		try {
			List<Pessoa> pessoas = pessoaService.findAll();
			return ResponseEntity.ok().body(pessoas);
		} catch (PessoaException e) {
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}
	}
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
		try {
			Pessoa pessoa =  pessoaService.findById(id);
			return ResponseEntity.ok().body(pessoa);
		} catch (PessoaException e) {
			e.getMessage();
			return (ResponseEntity<?>)ResponseEntity.noContent();
		}
	}
}
