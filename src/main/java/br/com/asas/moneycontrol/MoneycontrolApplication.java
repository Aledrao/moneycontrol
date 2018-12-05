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
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.asas.moneycontrol.controller", "br.com.asas.moneycontrol.service"})
@EnableJpaRepositories(basePackages = {"br.com.asas.moneycontrol.repository"})
@EntityScan(basePackages = {"br.com.asas.moneycontrol.entity"})
@EnableSwagger2
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
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.asas.moneycontrol.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Money Control")
				.description("Software de controle de gastos.")
				.version("0.0.1")
				.contact(new Contact("Alex Souza", "https://github.com/Aledrao/moneycontrol", "aalex_sandro@hotmail.com"))
				.build();
	}
}
