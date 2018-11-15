package br.com.limaisaias.mvcrud.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.limaisaias.mvcrud.event.RecursoCriadoEvent;
import br.com.limaisaias.mvcrud.model.Telefone;
import br.com.limaisaias.mvcrud.repository.TelefoneRepository;

@RestController
@RequestMapping("/telefones")
public class TelefoneResource {

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Telefone> listar() {
		return telefoneRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Telefone> criar(@Valid @RequestBody Telefone Telefone, HttpServletResponse response) {
		Telefone categoriaSalva = telefoneRepository.save(Telefone);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Telefone> buscarPeloId(@PathVariable Long id) {
		Optional<Telefone> Telefone = telefoneRepository.findById(id);
		return Telefone.isPresent() ? ResponseEntity.ok(Telefone.get()) : ResponseEntity.notFound().build();
	}
}