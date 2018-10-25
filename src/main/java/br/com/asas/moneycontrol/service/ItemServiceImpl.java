package br.com.asas.moneycontrol.service;	

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.exception.ItemException;
import br.com.asas.moneycontrol.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public List<Item> findAll() throws ItemException {
		List<Item> itens = (List<Item>) itemRepository.findAll();
		if(!itens.isEmpty()) {
			return itens;
		} else {
			throw new ItemException("Não há itens a apresentar.");
		}
	}

	@Override
	public Item findById(Long id) throws ItemException {
		boolean possuiItemPorId = itemRepository.existsById(id);
		if(possuiItemPorId) {
			Optional<Item> optimal = itemRepository.findById(id);
			Item item = optimal.get();
			return item;
		} else {
			throw new ItemException("Não foi possivel localizar o item pelo Id: " + id);
		}		
	}

	@Override
	public ResponseBean save(Item item) throws ItemException {
		if(item.getNome().equals("")) {
			throw new ItemException("O nome do item não foi informado.");
		} else {
			itemRepository.save(item);			
			return new ResponseBean("Item criado com sucesso.");
		}
	}

	@Override
	public ResponseBean update(Item item) throws ItemException {
		if(!findItem(item.getCodigo())) {
			throw new ItemException("Item não encontrado.");
		}
		itemRepository.saveAndFlush(item);
		return new ResponseBean("Item atualizado com sucesso.");
	}

	@Override
	public boolean delete(Long id) throws ItemException {
		if(!findItem(id)) {
			return false;
		}
		itemRepository.deleteById(id);
		return true;
	}
	
	@Override
	public boolean findItem(Long id) throws ItemException {
		boolean findItem = itemRepository.existsById(id);
		if(findItem) {
			return true;
		}
		return false;
	}
}
