package br.com.limaisaias.mvcrud.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@EqualsAndHashCode
public class Pessoa implements Serializable {

	private static final long serialVersionUID = -4330979957228328516L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String cpf;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@OneToMany(mappedBy = "pessoa", 
			fetch = FetchType.EAGER, 
			cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	private List<Telefone> telefones;

}
