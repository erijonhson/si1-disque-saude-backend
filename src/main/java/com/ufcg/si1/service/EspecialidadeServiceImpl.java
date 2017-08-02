package com.ufcg.si1.service;

import com.ufcg.si1.model.Especialidade;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoJaExistenteException;
import exceptions.Rep;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("especialidadeService")
public class EspecialidadeServiceImpl implements EspecialidadeService {

    private Especialidade[] vetor;

    private int indice;

    private int geraCodigo = 0; // para gerar codigos

    public EspecialidadeServiceImpl() {
        vetor = new Especialidade[100];
        indice = 0;
    }

    @Override
    public Especialidade procura(int codigo) throws Rep,
            ObjetoInexistenteException {

        int i = 0;

        while (i < indice) {
            if (vetor[i].getCodigo() == codigo) {
                return vetor[i];
            }

            i++;
        }

        throw new ObjetoInexistenteException("Erro Especialidade");
    }

    @Override
    public List getListaEspecialidade()
            throws Rep, ObjetoInexistenteException {
        return Arrays.asList(vetor);
    }

    @Override
    public int size() {
        return this.indice;
    }

    @Override
    public Especialidade getElemento(int posicao) {
        if (posicao < indice)
            return this.vetor[posicao];
        else
            return null;
    }

    @Override
    public void insere(Especialidade esp) throws Rep,
            ObjetoJaExistenteException {

        esp.setCodigo(++geraCodigo);

        if (indice == this.vetor.length) {
            throw new Rep("Erro ao incluir no array");
        }

        if (this.existe(esp.getCodigo())) {
            throw new ObjetoJaExistenteException("Objeto jah existe no array");
        }

        this.vetor[indice] = esp;
        indice++;
    }

    @Override
    public boolean existe(int codigo) {

        int indiceAux = 0;
        boolean existe = false;

        for (int i = 0; i < indice; i++) {
            if (this.vetor[i].getCodigo() == codigo) {
                indiceAux = i;
                existe = true;

                break;
            }
        }

        return existe;
    }

    public Especialidade findById(long id) {
        for (Especialidade esp: vetor) {
            if (esp.getCodigo() == id) {
                return esp;
            }
        }
        return null;
    }


}
