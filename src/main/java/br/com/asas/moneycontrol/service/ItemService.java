package br.com.asas.moneycontrol.service;

import java.util.List;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.exception.ItemException;

public interface ItemService {

	List<Item> findAll() throws ItemException;
	Item findById(Long id) throws ItemException;
	ResponseBean save(Item item) throws ItemException;
	ResponseBean update(Item item) throws ItemException;
	boolean delete(Long id) throws ItemException;
	boolean findItem(Long id) throws ItemException;
}
