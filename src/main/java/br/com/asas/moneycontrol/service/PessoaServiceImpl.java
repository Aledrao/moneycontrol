package br.com.asas.moneycontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.com.asas.moneycontrol.bean.ResponseBean;
import br.com.asas.moneycontrol.entity.Pessoa;
import br.com.asas.moneycontrol.exception.PessoaException;
import br.com.asas.moneycontrol.repository.PessoaRepository;

@Service
@Transactional(readOnly = true)
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<Pessoa> findAll() throws PessoaException {
		List<Pessoa> pessoas  = (List<Pessoa>)pessoaRepository.findAll();
		if(! CollectionUtils.isEmpty(pessoas)) {
			return pessoas;
		} else {
			throw new PessoaException("Não Pessoas há apresentar.");
		}
	}

	@Override
	public Pessoa findById(Long id) throws PessoaException {
		boolean possuiPessoaPorId = pessoaRepository.existsById(id);
		if(possuiPessoaPorId) {
			return pessoaRepository.getOne(id);
		} else {
			throw new PessoaException("Não foi possível localizar a Pessoa pelo Id: " +id);
		}
	}

	@Override
	public ResponseBean save(Pessoa pessoa) throws PessoaException {
		if(StringUtils.isEmpty(pessoa.getNome())) {
			throw new PessoaException("O nome da Pessoa Não foi informado.");
		} else {
			pessoaRepository.save(pessoa);
			return new ResponseBean("Pessoa criada com sucesso");
		}		
	}

	@Override
	public ResponseBean update(Pessoa pessoa) throws PessoaException {
		if(!findPessoa(pessoa.getCodigo())) {
			throw new PessoaException("Pessoa não encontrada.");
		} else {
			pessoaRepository.saveAndFlush(pessoa);
			return new ResponseBean("Pessoa atualizada com sucesso");
		}
	}

	@Override
	public boolean delete(Long id) throws PessoaException {
		if(!findPessoa(id)) {
			return false;
		}
		pessoaRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean findPessoa(Long id) throws PessoaException {
		boolean findPessoa = pessoaRepository.existsById(id);
		if(findPessoa) {
			return true;
		}
		return false;
	}
}
