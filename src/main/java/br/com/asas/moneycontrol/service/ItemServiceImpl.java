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
	public List<Item> findAll() {
		List<Item> itens = itemRepository.findAll();
		return itens;
	}

	@Override
	public Item findById(Long id) {
//		boolean possuiItemPorId = itemRepository.existsById(id);
		Optional<Item> optimal = itemRepository.findById(id);
		Item item = optimal.get();
		return item;
	}

	@Override
	public ResponseBean save(Item item) {
		itemRepository.save(item);
		return new ResponseBean("Item criado com sucesso.");
	}

	@Override
	public ResponseBean update(Item item) {
		if(findItem(item.getCodigo())) {
		itemRepository.saveAndFlush(item);
		return new ResponseBean("Item atualizado com sucesso.");
		} else {
			return new ResponseBean(402, "Não foi possivel localizar o item");
		}
	}

	@Override
	public boolean delete(Long id) {
		if (!findItem(id)) {
			return false;
		}
		itemRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean findItem(Long id) {
		boolean findItem = itemRepository.existsById(id);
		System.out.println("***************" + findItem + "***********************");
		if (findItem) {
			return true;
		}
		return false;
	}
}
