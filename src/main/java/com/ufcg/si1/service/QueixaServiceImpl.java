package com.ufcg.si1.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.model.Comentario;
import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.queixa.state.QueixaAberta;
import com.ufcg.si1.queixa.state.QueixaAndamento;
import com.ufcg.si1.repository.QueixaRepository;

@Service(value = "queixaService")
public class QueixaServiceImpl implements QueixaService {

	@Resource(name = "queixaRepository")
	QueixaRepository queixaRepository;

	@Resource(name = "cidadaoService")
	CidadaoService cidadaoService;

	@Resource(name = "enderecoService")
	EnderecoService enderecoService;

	@Resource(name = "comentarioService")
	ComentarioService comentarioService;

	@Override
	public Queixa cadastrar(Queixa queixa) {
		try {
			this.preparaQueixa(queixa);
			return queixaRepository.save(queixa);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_CONFLITO);
		}
	}

	@Override
	public Queixa atualizar(Queixa queixa) {
		try {
			return queixaRepository.save(queixa);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_CONFLITO);
		}
	}

	@Override
	public List<Queixa> buscarTodos() {
		List<Queixa> queixas = queixaRepository.findAll();
		if (queixas == null || queixas.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		return queixas;
	}

	@Override
	public Queixa buscarPorId(Long id) {
		Queixa queixa = queixaRepository.findOne(id);
		if (queixa == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		return queixa;
	}

	@Override
	public void deletar(Long id) {
		if (!queixaRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.QUEIXA_NAO_ENCONTRADA);
		}
		queixaRepository.delete(id);
	}

	public long quantidadeDeQueixas() {
		return queixaRepository.count();
	}

	public long quantidadeDeQueixasAbertas() {
		long totalAbertas = queixaRepository.countByState(new QueixaAberta());
		long totalEmAndamento = queixaRepository.countByState(new QueixaAndamento());
		return totalAbertas + totalEmAndamento;
	}

	@Override
	public Queixa mudaStateQueixa(Queixa queixa) {
		Queixa queixaBD = this.buscarPorId(queixa.getId());
		queixaBD.mudaStateQueixa();
		queixaBD = this.atualizar(queixaBD);
		return queixaBD;
	}

	@Override
	public Comentario adicionarComentario(Long idQueixa, Comentario comentario) {
		Queixa queixaBD = this.buscarPorId(idQueixa);
		comentario.setQueixa(queixaBD);
		return comentarioService.cadastrar(comentario);
	}

	@Override
	public Collection<Comentario> buscarComentariosDeQueixa(Long idQueixa) {
		return comentarioService.buscaComentariosDeQueixa(idQueixa);
	}

	private void preparaQueixa(Queixa queixa) {
		preparaSolicitanteDaQueixa(queixa);
		preparaEnderecoDaQueixa(queixa);
		queixa.setQueixaState(new QueixaAberta(queixa));
	}

	private void preparaSolicitanteDaQueixa(Queixa queixa) {
		Cidadao solicitante = queixa.getSolicitante();
		Cidadao solicitanteBD = null;
		try {
			solicitanteBD = cidadaoService.buscarPorEmail(solicitante.getEmail());
		} catch (Exception e) {
			solicitanteBD = cidadaoService.cadastrar(solicitante);
		}
		queixa.setSolicitante(solicitanteBD);
	}

	private void preparaEnderecoDaQueixa(Queixa queixa) {
		Endereco endereco = queixa.getEndereco();
		Endereco enderoBD = null;
		try {
			enderoBD = enderecoService.buscarPorRuaECidade(endereco);
		} catch (Exception e) {
			enderoBD = enderecoService.cadastrar(endereco);
		}
		queixa.setEndereco(enderoBD);
	}

}
