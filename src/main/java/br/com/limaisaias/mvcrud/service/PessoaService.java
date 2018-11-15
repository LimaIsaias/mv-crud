package br.com.limaisaias.mvcrud.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.limaisaias.mvcrud.model.Pessoa;
import br.com.limaisaias.mvcrud.repository.PessoaRepository;
import br.com.limaisaias.mvcrud.repository.TelefoneRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Transactional
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		excluirTelefones(pessoa, pessoaSalva);
		salvarNovosTelefones(pessoa);
		return pessoaRepository.save(pessoaSalva);
	}

	@Transactional
	public Pessoa save(Pessoa pessoa) {
		if (null != pessoa.getTelefones() && !pessoa.getTelefones().isEmpty()) {
			pessoa.getTelefones().forEach(t -> t.setPessoa(pessoa));
		}
		return pessoaRepository.save(pessoa);
	}

	/*********/

	private void excluirTelefones(Pessoa pessoa, Pessoa pessoaSalva) {
		pessoaSalva.getTelefones().stream().filter(tel -> containsTelefone(pessoa, tel.getId()))
				.forEach(tel -> telefoneRepository.delete(tel));
	}

	private boolean containsTelefone(Pessoa pessoa, Long id) {
		Long count = pessoa.getTelefones().stream().filter(tp -> tp.getId().equals(id)).count();
		return count == 0;
	}

	private void salvarNovosTelefones(Pessoa pessoa) {
		pessoa.getTelefones().forEach(tel -> {
			tel.setPessoa(pessoa);
			tel = telefoneRepository.save(tel);
		});
	}
}
