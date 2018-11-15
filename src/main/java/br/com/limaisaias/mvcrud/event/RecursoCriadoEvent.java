package br.com.limaisaias.mvcrud.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 650334674290959461L;

	@Getter
	private HttpServletResponse response;
	@Getter
	private Long id;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

}