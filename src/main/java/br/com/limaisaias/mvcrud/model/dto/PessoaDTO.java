package br.com.limaisaias.mvcrud.model.dto;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaDTO implements Serializable {
	private static final long serialVersionUID = 2240207734603234577L;
	private Long id;
	private String nome;
	private String cpf;
	private Integer idade;
	private Integer qtdTels;
	private Date dataNascimento;
	private String email;

	public PessoaDTO(Long id, String nome, String cpf, String email, Date dataNascimento, Long qtdTels) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.qtdTels = qtdTels.intValue();
		this.email = email;
	}

	public Integer getIdade() {
		try {
			DateTime dataAtual = new DateTime();
			DateTime dataNascimentoLocal = new DateTime(this.dataNascimento);
			Interval interval = new Interval(dataNascimentoLocal, dataAtual);
			this.idade = interval.toPeriod().getYears();
			return idade;
		} catch (Exception e) {
			return 0;
		}
	}

}
