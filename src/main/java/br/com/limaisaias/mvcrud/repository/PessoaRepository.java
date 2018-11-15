package br.com.limaisaias.mvcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.limaisaias.mvcrud.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
