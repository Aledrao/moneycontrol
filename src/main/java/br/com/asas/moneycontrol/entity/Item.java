package br.com.asas.moneycontrol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.bytebuddy.implementation.bind.annotation.Empty;

@Entity
@Table(name = "item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
	
	public Item() {
		super();
	}
	
	public Item(Long codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Item(String nome) {
		super();
		this.nome = nome;
	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_item")
	@Id
	private Long codigo;
	
	@NotEmpty(message="Informe o nome do item.")
	@Column(name = "item")
	private String nome;
	
	public Long getCodigo() {	
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
