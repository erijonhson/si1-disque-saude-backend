package com.ufcg.si1.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "P")
public class PostoSaude extends UnidadeSaude {
    
	@Column(name = "atendentes")
	private int atendentes;

	@Column(name = "taxa_Diaria_Atendimentos")
    private int taxaDiariaAtendimentos;

    public PostoSaude(String descricao, int at, int taxa) {
        super(descricao);
        this.atendentes = at;
        this.taxaDiariaAtendimentos = taxa;
    }

    public PostoSaude(){
        super();
    }

    // implementacoes vazias
    public int getAtendentes() {
        return atendentes;
    }

    public void setAtendentes(int atendentes) {
        this.atendentes = atendentes;
    }

    public int getTaxaDiariaAtendimentos() {
        return taxaDiariaAtendimentos;
    }

    public void setTaxaDiariaAtendimentos(int taxaDiariaAtendimentos) {
        this.taxaDiariaAtendimentos = taxaDiariaAtendimentos;
    }
}
