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

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_UnidadeSaude", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "U")
@Table(name = "tb_UnidadeSaude")
public class UnidadeSaude {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_Unidade_Saude;

	@Column(name = "descricao")
    private String descricao;

	@OneToMany(mappedBy = "unidade_saude")
	@JsonManagedReference
    private Set<Especialidade> especialidades;

	@Column(name = "num_queixas")
    private long [] numeroQueixas = new long[1000];

    public UnidadeSaude(String descricao) {// gerado no repositorio
        this.descricao = descricao;
        this.especialidades = new HashSet<>();
    }
    public UnidadeSaude(){
    }

    public String pegaDescricao() {
        return this.descricao;
    }

    public void mudaDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Especialidade> getEspecialidades() {
        return this.especialidades;
    }

    public void adicionarEspecialidade(Especialidade esp) {
        this.especialidades.add(esp);
    }

    public Long pegaCodigo() {
        return this.id_Unidade_Saude;
    }
}
