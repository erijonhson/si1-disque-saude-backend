package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Cidadao;
import com.ufcg.si1.model.Endereco;
import com.ufcg.si1.model.Queixa;
import com.ufcg.si1.model.SituacaoDeQueixa;
import com.ufcg.si1.model.prefeitura.PrefeituraSingleton;
import com.ufcg.si1.model.prefeitura.SituacaoGeralDasQueixas;
import com.ufcg.si1.repository.QueixaRepository;

@Service("queixaService")
public class QueixaServiceImpl implements QueixaService {

	@Autowired
	QueixaRepository queixaRepository;
	
	@Autowired
	GenericService<Cidadao> cidadaoService = new CidadaoServiceImpl();

	@Autowired
	GenericService<Endereco> enderecoService;

	@Override
	public Queixa cadastrar(Queixa queixa) {
		// TODO: avaliar nível de gambiarra disso 
		// por exemplo, usuário e endereço deveriam já estarem cadastrados, nesse ponto? 
		// devemos testar se existe, antes?
		// Acredito que essa é a maneira certa de fazer, de todo modo.
		enderecoService.cadastrar(queixa.getSolicitante().getEndereco());
		cidadaoService.cadastrar(queixa.getSolicitante());
		return queixaRepository.save(queixa);
	}

	@Override
	public Queixa atualizar(Queixa queixa) {
		return queixaRepository.save(queixa);
	}

	@Override
	public List<Queixa> buscarTodos() {
		return queixaRepository.findAll();
	}

	@Override
	public Queixa buscarPorId(Long id) {
		return queixaRepository.getOne(id);
	}

	@Override
	public void deletar(Queixa queixa) {
		queixaRepository.delete(queixa);
	}

	@Override
	public SituacaoGeralDasQueixas situacaoGeralDasQueixas() {

		PrefeituraSingleton prefeitura = PrefeituraSingleton.getInstance();

		float porcentagemQueixasAbertas = 
				queixaRepository.count() / queixaRepository.countBySituacao(SituacaoDeQueixa.ABERTA) * 100;
		return prefeitura.getSituacaoDasQueixas(porcentagemQueixasAbertas);
	}
	
	public Queixa fecharQueixa(Queixa queixa) {
		
		Queixa aFechar = buscarPorId(queixa.getId());
		aFechar.fechar();
		aFechar = atualizar(aFechar);
		
		return aFechar;
	}
}
