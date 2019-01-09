package br.com.asas.moneycontrol.service;

import java.util.List;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.exception.ItemException;

public interface ItemService {

	List<Item> findAll();
	Item findById(Long id);
	ResponseBean save(Item item);
	ResponseBean update(Item item);
	boolean delete(Long id);
	boolean findItem(Long id);
}
