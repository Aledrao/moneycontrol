package br.com.asas.moneycontrol.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Pessoa;
import br.com.asas.moneycontrol.exception.PessoaException;

public interface PessoaService {

	List<Pessoa> findAll() throws PessoaException;
	Pessoa findById(Long id) throws PessoaException;
	ResponseBean save(Pessoa pessoa) throws PessoaException;
	ResponseBean update(Pessoa pessoa) throws PessoaException;
	boolean delete (Long id) throws PessoaException;
	boolean findPessoa(Long id) throws PessoaException;
}
