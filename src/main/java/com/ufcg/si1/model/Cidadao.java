package com.ufcg.si1.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_Cidadao", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "C")
@Table(name = "tb_Cidadao")
public class Cidadao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;
	
	@Column(name = "ome")
	private String nome;
	
	@NotNull
	@Column(name = "email", nullable = false ,unique=true)
	private String email;
	
	@Column(name = "endereco")
	private Endereco endereco;

	@OneToMany(mappedBy = "cidadao")
	@JsonManagedReference
	private Set<Queixa> queixas;
	
	public Cidadao(){}


	public Cidadao(String nome, String email, String rua, String uf,
			String cidade) {
		this.endereco = new Endereco(rua, uf, cidade);
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setEndereço(String rua, String uf,String cidade) {
		endereco = new Endereco(rua, uf, cidade);
	}
	
	public Endereco getEndereco() {
		return this.endereco;
	}

	public void addQueixa(Queixa queixa) {
		if(queixas == null) {
			queixas = new HashSet<>();
		}
		
		queixas.add(queixa);
	}
	
	public Set<Queixa> getQueixas() {
		return queixas;
	}
}
