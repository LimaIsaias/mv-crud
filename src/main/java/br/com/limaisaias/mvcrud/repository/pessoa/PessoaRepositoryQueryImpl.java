package br.com.limaisaias.mvcrud.repository.pessoa;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.limaisaias.mvcrud.filter.PessoaFilter;
import br.com.limaisaias.mvcrud.model.dto.PessoaDTO;
import br.com.limaisaias.mvcrud.repository.AbstractRepository;

@Repository
public class PessoaRepositoryQueryImpl extends AbstractRepository implements PessoaRepositoryQuery {

	@Override
	public Page<PessoaDTO> findByFilter(PessoaFilter filter, Pageable pageable) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT new br.com.limaisaias.mvcrud.model.dto.PessoaDTO(p.id, p.nome, p.cpf, p.email, p.dataNascimento, (SELECT COUNT(t) FROM Telefone t WHERE t.pessoa = p ) ) ")
				.append(" FROM Pessoa p ").append(" WHERE 1 = 1 ");
		restrictions(filter, sql);
		TypedQuery<PessoaDTO> query = entityManager.createQuery(sql.toString(), PessoaDTO.class);
		createQuery(query, filter);
		paginate(pageable, query);
		return new PageImpl<PessoaDTO>(query.getResultList(), pageable, countByFilter(filter));
	}

	@Override
	public Long countByFilter(PessoaFilter filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(p) FROM Pessoa p WHERE 1 = 1 ");
		restrictions(filter, sql);
		Query query = entityManager.createQuery(sql.toString());
		createQuery(query, filter);
		try {
			return ((Number) query.getSingleResult()).longValue();
		} catch (Exception e) {
			return 0L;
		}
	}

	private void restrictions(PessoaFilter filter, StringBuilder sql) {
		if (StringUtils.isNotBlank(filter.getNome())) {
			sql.append(" AND p.nome LIKE :nome ");
		}
		if (StringUtils.isNotBlank(filter.getCpf())) {
			sql.append(" AND p.cpf = :cpf ");
		}
	}

	private Query createQuery(Query query, PessoaFilter filter) {

		if (StringUtils.isNotBlank(filter.getNome())) {
			query.setParameter("nome", "%" + filter.getNome() + "%");
		}
		if (StringUtils.isNotBlank(filter.getCpf())) {
			query.setParameter("cpf", filter.getCpf());
		}
		return query;
	}

	private void paginate(Pageable pageable, Query query) {
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
	}

}
