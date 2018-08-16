package br.com.asas.moneycontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.asas.moneycontrol.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
