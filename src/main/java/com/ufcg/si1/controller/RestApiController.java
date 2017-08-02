package com.ufcg.si1.controller;

import br.edu.ufcg.Hospital;
import com.ufcg.si1.model.*;
import com.ufcg.si1.service.*;
import com.ufcg.si1.util.CustomErrorType;
import com.ufcg.si1.util.ObjWrapper;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;
import exceptions.ObjetoJaExistenteException;
import exceptions.Rep;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

    QueixaService queixaService = new QueixaServiceImpl();
    EspecialidadeService especialidadeService = new EspecialidadeServiceImpl();
    UnidadeSaudeService unidadeSaudeService = new UnidadeSaudeServiceImpl();

    /* situação normal =0
       situação extra =1
     */
    private int situacaoAtualPrefeitura = 0;


    // -------------------Retrieve All Complaints---------------------------------------------

    @RequestMapping(value = "/queixa/", method = RequestMethod.GET)
    public ResponseEntity<List<Queixa>> listAllUsers() {
        List<Queixa> queixas = queixaService.findAllQueixas();

        if (queixas.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Queixa>>(queixas, HttpStatus.OK);
    }

    // -------------------Abrir uma Queixa-------------------------------------------

    @RequestMapping(value = "/queixa/", method = RequestMethod.POST)
    public ResponseEntity<?> abrirQueixa(@RequestBody Queixa queixa, UriComponentsBuilder ucBuilder) {

        //este codigo estava aqui, mas nao precisa mais
        /*if (queixaService.doesQueixaExist(queixa)) {
			return new ResponseEntity(new CustomErrorType("Esta queixa já existe+
					queixa.pegaDescricao()),HttpStatus.CONFLICT);
		}*/

        try {
            queixa.abrir();
        } catch (ObjetoInvalidoException e) {
            return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);
        }
        queixaService.saveQueixa(queixa);

       // HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/api/queixa/{id}").buildAndExpand(queixa.getId()).toUri());

        return new ResponseEntity<Queixa>(queixa, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/queixa/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarQueixa(@PathVariable("id") long id) {

        Queixa q = queixaService.findById(id);
        if (q == null) {
            return new ResponseEntity(new CustomErrorType("Queixa with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Queixa>(q, HttpStatus.OK);
    }


    @RequestMapping(value = "/queixa/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateQueixa(@PathVariable("id") long id, @RequestBody Queixa queixa) {

        Queixa currentQueixa = queixaService.findById(id);

        if (currentQueixa == null) {
            return new ResponseEntity(new CustomErrorType("Unable to upate. Queixa with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentQueixa.setDescricao(queixa.getDescricao());
        currentQueixa.setComentario(queixa.getComentario());

        queixaService.updateQueixa(currentQueixa);
        return new ResponseEntity<Queixa>(currentQueixa, HttpStatus.OK);
    }

    @RequestMapping(value = "/queixa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

        Queixa user = queixaService.findById(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Queixa with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        queixaService.deleteQueixaById(id);
        return new ResponseEntity<Queixa>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/queixa/fechamento", method = RequestMethod.POST)
    public ResponseEntity<?> fecharQueixa(@RequestBody Queixa queixaAFechar) {
        queixaAFechar.situacao = Queixa.FECHADA;
        queixaService.updateQueixa(queixaAFechar);
        return new ResponseEntity<Queixa>(queixaAFechar, HttpStatus.OK);
    }


    //Especialidade

    @RequestMapping(value = "/especialidade/unidades", method = RequestMethod.GET)
    public ResponseEntity<?> consultaEspecialidadeporUnidadeSaude(@RequestBody int codigoUnidadeSaude) {

        Object us = null;
        try {
            us = unidadeSaudeService.procura(codigoUnidadeSaude);
        } catch (Rep e) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        } catch (ObjetoInexistenteException e) {
            return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        }
        if (us instanceof UnidadeSaude){
            UnidadeSaude us1 = (UnidadeSaude) us;
            return new ResponseEntity<>(us1.getEspecialidades(), HttpStatus.OK);
        }

        return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/unidade/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUnidades() {
        List<Object> unidades = unidadeSaudeService.getAll();
        if (unidades.isEmpty()) return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
        else{
            List<UnidadeSaude> unidadeSaudes = new ArrayList<>();
            for (Object  saude: unidades) {
                if(saude instanceof UnidadeSaude){
                    unidadeSaudes.add((UnidadeSaude) saude);
                }
            }
            return new ResponseEntity<>(unidadeSaudes, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/especialidade/", method = RequestMethod.POST)
    public ResponseEntity<String> incluirEspecialidade(@RequestBody Especialidade esp, UriComponentsBuilder ucBuilder) {
        try {
            especialidadeService.insere(esp);
        } catch (Rep e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (ObjetoJaExistenteException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/especialidade/{id}").buildAndExpand(esp.getCodigo()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    //how to save a subclass object?
    @RequestMapping(value = "/unidade/", method = RequestMethod.POST)
    public ResponseEntity<String> incluirUnidadeSaude(@RequestBody UnidadeSaude us, UriComponentsBuilder ucBuilder) {

        try {
            unidadeSaudeService.insere(us);
        } catch (Rep e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (ObjetoJaExistenteException e) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/unidade/{id}").buildAndExpand(us.pegaCodigo()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/especialidade/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarEspecialidade(@PathVariable("id") long id) {

        Especialidade q = especialidadeService.findById(id);
        if (q == null) {
            return new ResponseEntity(new CustomErrorType("Especialidade with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Especialidade>(q, HttpStatus.OK);
    }

    @RequestMapping(value = "/unidade/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarUnidadeSaude(@PathVariable("id") long id) {

        Object us = unidadeSaudeService.findById(id);
        if (us == null) {
            return new ResponseEntity(new CustomErrorType("Unidade with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(us, HttpStatus.OK);
    }


    @RequestMapping(value = "/geral/medicos/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> calcularMediaMedicoPacienteDia(@PathVariable("id") long id) {

        Object unidade = unidadeSaudeService.findById(id);

        if(unidade == null){
            return new ResponseEntity<ObjWrapper<Double>>(HttpStatus.NOT_FOUND);
        }

        double c = 0.0;
        if (unidade instanceof PostoSaude)
            c = ((PostoSaude) unidade).getAtendentes()
                    / ((PostoSaude) unidade).taxaDiaria();
        else if (unidade instanceof Hospital){
            c = ((Hospital) unidade).getNumeroMedicos()
                    / ((Hospital) unidade).getNumeroPacientesDia();
        }
        return new ResponseEntity<ObjWrapper<Double>>(new ObjWrapper<Double>(new Double(c)), HttpStatus.OK);
    }

    @RequestMapping(value = "/geral/situacao", method = RequestMethod.GET)
    public ResponseEntity<?> getSituacaoGeralQueixas() {

        // dependendo da situacao da prefeitura, o criterio de avaliacao muda
        // se normal, mais de 20% abertas eh ruim, mais de 10 eh regular
        // se extra, mais de 10% abertas eh ruim, mais de 5% eh regular
        if (situacaoAtualPrefeitura == 0) {
            if ((double) numeroQueixasAbertas() / queixaService.size() > 0.2) {
                return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(0), HttpStatus.OK);
            } else {
                if ((double) numeroQueixasAbertas() / queixaService.size() > 0.1) {
                    return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(1), HttpStatus.OK);
                }
            }
        }
        if (this.situacaoAtualPrefeitura == 1) {
            if ((double) numeroQueixasAbertas() / queixaService.size() > 0.1) {
                return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(0), HttpStatus.OK);
            } else {
                if ((double) numeroQueixasAbertas() / queixaService.size() > 0.05) {
                    return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(1), HttpStatus.OK);
                }
            }
        }

        //situacao retornada
        //0: RUIM
        //1: REGULAR
        //2: BOM
        return new ResponseEntity<ObjWrapper<Integer>>(new ObjWrapper<Integer>(2), HttpStatus.OK);
    }

    @RequestMapping(value="/unidade/busca", method= RequestMethod.GET)
    public ResponseEntity<?> consultarUnidadeSaudePorBairro(@RequestParam(value = "bairro", required = true) String bairro){
        Object us = unidadeSaudeService.findByBairro(bairro);
        if (us == null && !(us instanceof UnidadeSaude)) {
            return new ResponseEntity(new CustomErrorType("Unidade with bairro " + bairro
                    + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<UnidadeSaude>((UnidadeSaude) us, HttpStatus.OK);
    }

    private double numeroQueixasAbertas() {
        int contador = 0;
        Iterator<Queixa> it = queixaService.getIterator();
        for (Iterator<Queixa> it1 = it; it1.hasNext(); ) {
            Queixa q = it1.next();
            if (q.getSituacao() == Queixa.ABERTA)
                contador++;
        }

        return contador;
    }

}

