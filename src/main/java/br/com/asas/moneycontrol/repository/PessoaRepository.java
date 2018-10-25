package br.com.asas.moneycontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.asas.moneycontrol.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
