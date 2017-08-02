package com.ufcg.si1;
 
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import com.ufcg.si1.model.*;
import com.ufcg.si1.util.ObjWrapper;
import org.springframework.web.client.RestTemplate;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:5000/SpringBootRestApi/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllQueixas(){
        System.out.println("Testing listAllQueixas API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> queixasMap = restTemplate.getForObject(REST_SERVICE_URI+"/queixa/", List.class);
         
        if(queixasMap!=null){
            for(LinkedHashMap<String, Object> map : queixasMap){
                String out = "Queixa : id="+map.get("id")+", Description="+map.get("descricao")+", " +
                        "Status="+map.get("situacao")+", Commentary="+map.get("comentario")+", ";
                LinkedHashMap<String,Object> internalMap = (LinkedHashMap<String, Object>) map.get("solicitante");
                out = out + "Name: "+internalMap.get("nome") + ", City="+internalMap.get("cidade")+", " +
                        "Email="+internalMap.get("email")+", Street="+internalMap.get("rua")+", ST="+internalMap.get("uf");
                System.out.println(out);

            }
        }else{
            System.out.println("No queixa exist----------");
        }
    }


    /* GET*/
    private static void getQueixa(){
        System.out.println("Testing consultarQueixa API----------");
        RestTemplate restTemplate = new RestTemplate();
        Queixa queixa = restTemplate.getForObject(REST_SERVICE_URI+"/queixa/1", Queixa.class);
        System.out.println(queixa);
    }
     
    /* POST */
    private static void createQueixa() {
        System.out.println("Testing create Queixa API----------");
        RestTemplate restTemplate = new RestTemplate();

        //criando queixa com -1, para gerar codigo depois
        Queixa q = new Queixa(-1,"Muitos ratos no meio da rua",1,"",
                "Jorge de Baixinho", "jorginho@gmail.com", "rua dos bobos", "SP",
                "São Paulo");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/queixa/", q, Queixa.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateQueixa() {
        System.out.println("Testing update Queixa API----------");
        RestTemplate restTemplate = new RestTemplate();
        Queixa q = new Queixa(1,"Nova queixa com ID 1",1,"",
                "Jorge de Baixinho", "jorginho@gmail.com", "rua dos bobos", "SP",
                "São Paulo");
        restTemplate.put(REST_SERVICE_URI+"/queixa/1", q);
        System.out.println(q);
    }
 
    /* DELETE */
    private static void deleteUser() {
        System.out.println("Testing delete Queixa API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/queixa/3");
    }

    public static void main(String args[]){
        listAllQueixas();
        /*getQueixa();*/
        //createQueixa();
        //updateQueixa();
        //deleteUser();
        //listAllQueixas();
        //fecharQueixa(1);
        //insereUnidadesEsps();
        //testeDeCalculo();
        //testeSituacao();
    }

    private static void testeDeCalculo() {

        System.out.println("\nTaxa de medico por paciente-dia nas unidades");
        RestTemplate restTemplate = new RestTemplate();
        for (int i=1;i<=4;i++) {
            ObjWrapper<Double> retorno = restTemplate.getForObject(REST_SERVICE_URI + "/geral/medicos/" + i, ObjWrapper.class);
            System.out.println(retorno.getObj());
        }

    }

    private static void testeSituacao(){
        System.out.println("\nTaxa situação");
        RestTemplate restTemplate = new RestTemplate();
        ObjWrapper<Integer> retorno = restTemplate.getForObject(REST_SERVICE_URI + "/geral/situacao/", ObjWrapper.class);
        System.out.println(retorno.getObj());

    }


    /*private static void insereUnidadesEsps() {
        Especialidade cli = new Especialidade("Clinica Geral");
        Especialidade oft = new Especialidade("Oftalmologia");
        Especialidade cir = new Especialidade("Cirurgia");

        System.out.println("Testing especialidades----------");
        RestTemplate restTemplate = new RestTemplate();

        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/especialidade/", cli, Especialidade.class);
        System.out.println("Location : "+uri.toASCIIString());
        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/especialidade/", oft, Especialidade.class);
        System.out.println("Location : "+uri.toASCIIString());
        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/especialidade/", cir, Especialidade.class);
        System.out.println("Location : "+uri.toASCIIString());


        UnidadeSaude u1 = new Hospital("Sao Joao", 10, 500);
        u1.adicionarEspecialidade(cli);
        u1.adicionarEspecialidade(cir);
        UnidadeSaude u2 = new Hospital("Geral", 50, 120);
        u2.adicionarEspecialidade(cli);
        u2.adicionarEspecialidade(oft);
        UnidadeSaude u3 = new PostoSaude("Varzea Grande", 2, 200);
        u3.adicionarEspecialidade(cli);
        UnidadeSaude u4 = new PostoSaude("Torre", 4, 130);
        u4.adicionarEspecialidade(cli);

        System.out.println("Testing unidades----------");

        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/unidade/", u1, Hospital.class);
        System.out.println("Location : "+uri.toASCIIString());
        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/unidade/", u2, Hospital.class);
        System.out.println("Location : "+uri.toASCIIString());
        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/unidade/", u3, PostoSaude.class);
        System.out.println("Location : "+uri.toASCIIString());
        uri = restTemplate.postForLocation(REST_SERVICE_URI+"/unidade/", u4, PostoSaude.class);
        System.out.println("Location : "+uri.toASCIIString());

    }
*/
    private static void fecharQueixa(long i) {
        System.out.println("Testing fechar Queixa API----------");
        RestTemplate restTemplate = new RestTemplate();
        Queixa queixa = restTemplate.getForObject(REST_SERVICE_URI+"/queixa/"+i, Queixa.class);


        restTemplate.postForLocation(REST_SERVICE_URI+"/queixa/fechamento", queixa, Queixa.class);
        queixa = restTemplate.getForObject(REST_SERVICE_URI+"/queixa/"+i, Queixa.class);
        System.out.println(queixa.situacao);

    }



}