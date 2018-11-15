package br.com.limaisaias.mvcrud.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Telefone implements Serializable{

	private static final long serialVersionUID = -560587453059306557L;

	@NotBlank
	@Size(min = 2, max = 3)
	private String ddd;

	@NotBlank
	@Size(min = 8, max = 9)
	private String numero;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	

}