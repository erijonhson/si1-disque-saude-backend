package com.ufcg.si1.service;

import com.ufcg.si1.model.Especialidade;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoJaExistenteException;
import exceptions.Rep;

import java.util.List;


public interface EspecialidadeService {
    Especialidade procura(int codigo) throws Rep,
            ObjetoInexistenteException;

    List getListaEspecialidade()
                    throws Rep, ObjetoInexistenteException;

    int size();

    Especialidade getElemento(int posicao);

    void insere(Especialidade esp)throws Rep,
            ObjetoJaExistenteException;

    boolean existe(int codigo);

    Especialidade findById(long id);
}
