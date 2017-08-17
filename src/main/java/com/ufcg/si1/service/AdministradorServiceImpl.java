package com.ufcg.si1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.si1.model.Administrador;
import com.ufcg.si1.repository.AdministradorRepository;

@Service("administradorService")
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	AdministradorRepository administradorRepository;

	@Override
	public Administrador cadastrar(Administrador administrador) {
		return administradorRepository.save(administrador);
	}

	@Override
	public Administrador atualizar(Administrador administrador) {
		Administrador administradorBD = 
				administradorRepository.findByEmail(administrador.getEmail());
		if (administradorBD != null)
			return administradorRepository.save(administradorBD);
		else
			return administradorRepository.save(administrador);
	}

	@Override
	public List<Administrador> buscarTodos() {
		return administradorRepository.findAll();
	}

	@Override
	public Administrador buscarPorId(Long id) {
		return administradorRepository.findOne(id);
	}

	@Override
	public void deletar(Administrador administrador) {
		administradorRepository.delete(administrador);
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
			throw new RuntimeException("Email ou senha inválidos!");
		}

		administradorBD.setSenha(null);

		return administradorBD;
	}

}
