package com.ufcg.si1.model;


public class PostoSaude extends UnidadeSaude{
    private int atendentes;

    private float taxaDiariaAtendimentos;

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

    public float taxaDiaria() {
        return taxaDiariaAtendimentos;
    }

    public void setAtendentes(int atendentes) {
        this.atendentes = atendentes;
    }

    public float getTaxaDiariaAtendimentos() {
        return taxaDiariaAtendimentos;
    }

    public void setTaxaDiariaAtendimentos(float taxaDiariaAtendimentos) {
        this.taxaDiariaAtendimentos = taxaDiariaAtendimentos;
    }
}
