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
			throw new ConflictRuntimeException("Administrador já existe!");
		}
	}

	@Override
	public Administrador atualizar(Administrador administrador) {
		try {
			Administrador administradorBD = 
					administradorRepository.findByEmail(administrador.getEmail());
			if (administradorBD != null)
				return administradorRepository.save(administradorBD);
			else
				return administradorRepository.save(administrador);
		} catch (Exception e) {
			throw new ConflictRuntimeException("Erro na atualização!");
		}
	}

	@Override
	public List<Administrador> buscarTodos() {
		List<Administrador> administradores = administradorRepository.findAll();
		if (administradores == null) {
			throw new NotFoundRuntimeException(ConstantesDeErro.ADMINISTRADOR_NAO_ENCONTRADO);
		}
		return administradores;
	}

	@Override
	public Administrador buscarPorId(Long id) {
		return administradorRepository.findOne(id);
	}

	@Override
	public void deletar(Long id) {

		if (!administradorRepository.exists(id)) {
			throw new ConflictRuntimeException("Administrador inexistente ou inválido!");
		}

		administradorRepository.delete(id);
	}

	@Override
	public Administrador buscarPorEmail(String email) {
		return administradorRepository.findByEmail(email);
	}

	public Administrador login(Administrador administrador) {

		Administrador administradorBD = 
				administradorRepository.findByEmailAndSenha(
						administrador.getEmail(), administrador.getSenha());

		if (administradorBD == null) {
			throw new LoginRuntimeException("Email ou senha inválidos!");
		}

		administradorBD.setSenha(null);

		return administradorBD;
	}

}
