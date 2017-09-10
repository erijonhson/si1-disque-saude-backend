package com.ufcg.si1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ufcg.si1.exception.ConflictRuntimeException;
import com.ufcg.si1.exception.ConstantesDeErro;
import com.ufcg.si1.exception.LoginRuntimeException;
import com.ufcg.si1.exception.NotFoundRuntimeException;
import com.ufcg.si1.model.Administrador;
import com.ufcg.si1.repository.AdministradorRepository;

@Service(value = "administradorService")
public class AdministradorServiceImpl implements AdministradorService {

	@Resource(name = "administradorRepository")
	AdministradorRepository administradorRepository;

	@Override
	public Administrador cadastrar(Administrador administrador) {
		try {
			return administradorRepository.save(administrador);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.ADMINISTRADOR_CONFLITO);
		}
	}

	@Override
	public Administrador atualizar(Administrador administrador) {
		try {
			Administrador administradorBD = 
					administradorRepository.findByEmail(administrador.getEmail());
			if (administradorBD != null) {
				administrador = administradorBD;
			}
			return administradorRepository.save(administrador);
		} catch (Exception e) {
			throw new ConflictRuntimeException(ConstantesDeErro.ADMINISTRADOR_CONFLITO);
		}
	}

	@Override
	public List<Administrador> buscarTodos() {
		List<Administrador> administradores = administradorRepository.findAll();
		if (administradores == null || administradores.isEmpty()) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ADMINISTRADOR_NAO_ENCONTRADO);
		}
		return administradores;
	}

	@Override
	public Administrador buscarPorId(Long id) {
		Administrador administrador = administradorRepository.findOne(id);
		return verifyNotFound(administrador);
	}

	@Override
	public void deletar(Long id) {
		if (!administradorRepository.exists(id)) {
			throw new ConflictRuntimeException(ConstantesDeErro.ADMINISTRADOR_NAO_ENCONTRADO);
		}
		administradorRepository.delete(id);
	}

	@Override
	public Administrador buscarPorEmail(String email) {
		Administrador administrador = administradorRepository.findByEmail(email);
		return verifyNotFound(administrador);
	}

	public Administrador login(Administrador administrador) {
		Administrador administradorBD = 
				administradorRepository.findByEmailAndSenha(
						administrador.getEmail(), administrador.getSenha());
		if (administradorBD == null) {
			throw new LoginRuntimeException(ConstantesDeErro.LOGIN_INVALIDO);
		}
		administradorBD.setSenha(null);
		return administradorBD;
	}

	private Administrador verifyNotFound(Administrador administrador) {
		if (administrador == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ADMINISTRADOR_NAO_ENCONTRADO);
		}
		return administrador;
	}

}
