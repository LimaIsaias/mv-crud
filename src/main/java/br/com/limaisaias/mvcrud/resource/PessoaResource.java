package br.com.limaisaias.mvcrud.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.limaisaias.mvcrud.event.RecursoCriadoEvent;
import br.com.limaisaias.mvcrud.filter.PessoaFilter;
import br.com.limaisaias.mvcrud.model.Pessoa;
import br.com.limaisaias.mvcrud.model.dto.PessoaDTO;
import br.com.limaisaias.mvcrud.repository.PessoaRepository;
import br.com.limaisaias.mvcrud.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<PessoaDTO>> findByFilters(PessoaFilter filter, Pageable pageable) {
		Page<PessoaDTO> pessoas = pessoaRepository.findByFilter(filter, pageable);
		return null != pessoas && pessoas.hasContent() ? ResponseEntity.ok(pessoas)
				: ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa Pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.save(Pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		return ResponseEntity.ok(pessoaService.atualizar(id, pessoa));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPeloId(@PathVariable Long id) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}

}