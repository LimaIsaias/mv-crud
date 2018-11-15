package br.com.limaisaias.mvcrud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Pessoa implements Serializable {

	private static final long serialVersionUID = -4330979957228328516L;

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private Date dataNascimento;
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<Telefone> telefones;

}
