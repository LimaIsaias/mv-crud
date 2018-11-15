package br.com.limaisaias.mvcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.limaisaias.mvcrud.model.Pessoa;
import br.com.limaisaias.mvcrud.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{

}
