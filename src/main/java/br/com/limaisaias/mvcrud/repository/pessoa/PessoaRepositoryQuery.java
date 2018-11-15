package br.com.limaisaias.mvcrud.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.limaisaias.mvcrud.filter.PessoaFilter;
import br.com.limaisaias.mvcrud.model.dto.PessoaDTO;

public interface PessoaRepositoryQuery {

	Page<PessoaDTO> findByFilter(PessoaFilter filter, Pageable pageable);

	Long countByFilter(PessoaFilter filter);
}
