package br.com.limaisaias.mvcrud.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telefone")
@Getter
@Setter
@EqualsAndHashCode
public class Telefone implements Serializable{

	private static final long serialVersionUID = -560587453059306557L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 2, max = 3)
	private String ddd;

	@NotBlank
	@Size(min = 8, max = 9)
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	
	public Telefone() {
	}
	
	public Telefone(String ddd, String numero, Pessoa pessoa) {
		super();
		this.ddd = ddd;
		this.numero = numero;
		this.pessoa = pessoa;
	}

	@JsonIgnore
	public Pessoa getPessoa() {
		return pessoa;
	}
}
