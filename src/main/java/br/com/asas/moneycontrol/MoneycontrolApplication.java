package br.com.asas.moneycontrol;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.repository.ItemRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.asas.moneycontrol.controller", "br.com.asas.moneycontrol.service"})
@EnableJpaRepositories(basePackages = {"br.com.asas.moneycontrol.repository"})
@EntityScan(basePackages = {"br.com.asas.moneycontrol.entity"})
public class MoneycontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneycontrolApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner setup(ItemRepository itemRepository) {
		return (args) -> {
			itemRepository.save(new Item("Transporte"));
			itemRepository.save(new Item("Gás"));
			itemRepository.save(new Item("Guloseima"));
		};
	}
}
