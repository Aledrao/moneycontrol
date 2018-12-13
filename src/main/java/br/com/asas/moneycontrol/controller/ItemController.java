package br.com.asas.moneycontrol.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.exception.ItemException;
import br.com.asas.moneycontrol.service.ItemService;

@RestController
@RequestMapping(value = "/itens")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> all() {
		try {
			List<Item> itens = itemService.findAll();
			return ResponseEntity.ok().body(itens);
		} catch (ItemException e) {
			e.getMessage();
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
		try {
			Item item = itemService.findById(id);
			return ResponseEntity.ok().body(item);
		} catch (ItemException e) {
			e.getMessage();
			return (ResponseEntity<?>) ResponseEntity.noContent();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@Valid @RequestBody Item item, Errors errors) {
		try {
			if(errors.hasErrors()) {
				return ResponseEntity.ok(new ResponseBean(402, errors.getFieldError().getDefaultMessage()));
			}
			ResponseBean response = itemService.save(item);
			return ResponseEntity.ok(response);
		} catch (ItemException e) {
			e.getMessage();
			return ResponseEntity.ok(new ResponseBean(502, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody Item item, Errors errors) {
		try {
			if(errors.hasErrors()) {
				return ResponseEntity.ok(new ResponseBean(402, errors.getFieldError().getDefaultMessage()));
			}
			ResponseBean response = itemService.update(item);
			return ResponseEntity.ok(response);
		} catch (ItemException e) {
			e.getMessage();
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws ItemException {
		try {
			boolean response = itemService.delete(id);
			return ResponseEntity.ok(response);			
		} catch (Exception e) {
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}
	}
}
