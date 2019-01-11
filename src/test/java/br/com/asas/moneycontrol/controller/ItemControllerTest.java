package br.com.asas.moneycontrol.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.asas.moneycontrol.MoneycontrolApplication;
import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Item;
import br.com.asas.moneycontrol.service.ItemServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MoneycontrolApplication.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private ItemServiceImpl itemServiceMock;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void deveriaApresentarTodaListaItens() throws Exception {
			Item itemUm = new Item();
			itemUm.setCodigo(1L);
			itemUm.setNome("Transporte");

			Item itemDois = new Item();
			itemDois.setCodigo(2L);
			itemDois.setNome("Gás");

			Item itemTres = new Item();
			itemTres.setCodigo(3L);
			itemTres.setNome("Guloseima");

			when(itemServiceMock.findAll()).thenReturn(Arrays.asList(itemUm, itemDois, itemTres));

			mockMvc.perform(MockMvcRequestBuilders.get("/itens").accept(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].codigo", is(1)))
					.andExpect(jsonPath("$[0].nome", is("Transporte"))).andExpect(jsonPath("$[1].codigo", is(2)))
					.andExpect(jsonPath("$[1].nome", is("Gás"))).andExpect(jsonPath("$[2].codigo", is(3)))
					.andExpect(jsonPath("$[2].nome", is("Guloseima")));
	}

	@Test
	public void deveriaApresentarItemAtravesId() throws Exception {
		Item item = new Item();
		item.setCodigo(1L);
		item.setNome("Transporte");

		when(itemServiceMock.findItem(1L)).thenReturn(true);
		when(itemServiceMock.findById(1L)).thenReturn(item);		

		mockMvc.perform(get("/itens/1")).andExpect(
				status()
				.isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.codigo").value(1L)).andExpect(jsonPath("$.nome").value("Transporte"))
				.andDo(print());
	}
	
	@Test
	public void naoDeveriaEncontrarItemAtravesId() throws Exception {
		Item item = new Item();
		item.setCodigo(1L);
		item.setNome("Transporte");

		when(itemServiceMock.findItem(8L)).thenReturn(false);
		when(itemServiceMock.findById(8L)).thenReturn(new Item());		

		mockMvc.perform(get("/itens/8")).andExpect(
				status()
				.isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.codigo").value(402L)).andExpect(jsonPath("$.mensagem").value("Não foi possivel localizar o item, com o código: 8"))
				.andDo(print());
	}

	@Test
	public void deveriaSalvarItem() throws Exception {
		Item item = new Item();
		item.setNome("Teste");

		ResponseBean response = new ResponseBean("Item criado com sucesso");

		when(itemServiceMock.save(any(Item.class))).thenReturn(response);

		mockMvc.perform(post("/itens").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"codigo\" : null, \"nome\" : \"Teste\" }")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.codigo", is(0)))
				.andExpect(jsonPath("$.mensagem", is("Item criado com sucesso")));

		ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemServiceMock, times(1)).save(itemCaptor.capture());
		verifyNoMoreInteractions(itemServiceMock);

		ResponseBean responseBean = new ResponseBean("Item criado com sucesso");
		assertEquals(responseBean.getCodigo(), 0);
		assertEquals(responseBean.getMensagem(), "Item criado com sucesso");
	}

	@Test
	public void deveriaAtualizarItem() throws Exception {
		Item item = new Item();
		item.setCodigo(1L);
		item.setNome("Teste");

		ResponseBean response = new ResponseBean("Item atualizado com sucesso.");

		when(itemServiceMock.update(any(Item.class))).thenReturn(response);

		mockMvc.perform(put("/itens/1").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"codigo\" : 1, \"nome\" : \"Segundo\" }")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.codigo", is(0)))
				.andExpect(jsonPath("$.mensagem", is("Item atualizado com sucesso.")));

		ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemServiceMock, times(1)).update(itemCaptor.capture());
		verifyNoMoreInteractions(itemServiceMock);

		ResponseBean responseBean = new ResponseBean("Item atualizado com sucesso");
		assertEquals(responseBean.getCodigo(), 0);
		assertEquals(responseBean.getMensagem(), "Item atualizado com sucesso");
	}

	@Test
	public void deveriaExcluirItem() throws Exception {
		boolean response = true;

		when(itemServiceMock.delete(1L)).thenReturn(response);

		mockMvc.perform(delete("/itens/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", is(true)));

		verify(itemServiceMock, times(1)).delete(1L);
		verifyNoMoreInteractions(itemServiceMock);

		boolean retorno = true;
		assertEquals(retorno, true);
	}

	@Test
	public void naoDeveriaSalvarItem() throws Exception {
		Item item = new Item();
		item.setNome("");

		mockMvc.perform(post("/itens").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"codigo\" : null, \"nome\" : \"\" }")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.codigo", is(402)))
				.andExpect(jsonPath("$.mensagem", is("Informe o nome do item.")));

		ResponseBean responseBean = new ResponseBean(402, "Informe o nome do item.");
		assertEquals(responseBean.getCodigo(), 402);
		assertEquals(responseBean.getMensagem(), "Informe o nome do item.");
	}

	@Test
	public void naoDeveriaAtualizarItem() throws Exception {
		mockMvc.perform(
				put("/itens/1").contentType(MediaType.APPLICATION_JSON).content("{ \"codigo\" : 1, \"nome\" : \"\" }"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.codigo", is(402)))
				.andExpect(jsonPath("$.mensagem", is("Informe o nome do item.")));

		ResponseBean responseBean = new ResponseBean(402, "Informe o nome do item.");
		assertEquals(responseBean.getCodigo(), 402);
		assertEquals(responseBean.getMensagem(), "Informe o nome do item.");
	}
}
