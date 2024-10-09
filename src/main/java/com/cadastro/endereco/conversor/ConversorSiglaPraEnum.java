package com.cadastro.endereco.conversor;
import java.util.HashMap;
import java.util.Map;

import com.cadastro.endereco.modelo.enums.EstadoEndereco;

public class ConversorSiglaPraEnum {
	
	private static final Map<String, EstadoEndereco> mapaEstados = new HashMap<>();
	static {
	    mapaEstados.put("AC", EstadoEndereco.Acre);
	    mapaEstados.put("AL", EstadoEndereco.Alagoas);
	    mapaEstados.put("AP", EstadoEndereco.Amapá);
	    mapaEstados.put("AM", EstadoEndereco.Amazonas);
	    mapaEstados.put("BA", EstadoEndereco.Bahia);
	    mapaEstados.put("CE", EstadoEndereco.Ceará);
	    mapaEstados.put("DF", EstadoEndereco.Distrito_Federal);
	    mapaEstados.put("ES", EstadoEndereco.Espírito_Santo);
	    mapaEstados.put("GO", EstadoEndereco.Goiás);
	    mapaEstados.put("MA", EstadoEndereco.Maranhão);
	    mapaEstados.put("MT", EstadoEndereco.Mato_Grosso);
	    mapaEstados.put("MS", EstadoEndereco.Mato_Grosso_do_Sul);
	    mapaEstados.put("MG", EstadoEndereco.Minas_Gerais);
	    mapaEstados.put("PA", EstadoEndereco.Paraná); 
	    mapaEstados.put("PB", EstadoEndereco.Paraíba);
	    mapaEstados.put("PR", EstadoEndereco.Pará);
	    mapaEstados.put("PE", EstadoEndereco.Pernambuco);
	    mapaEstados.put("PI", EstadoEndereco.Piauí);
	    mapaEstados.put("RJ", EstadoEndereco.Rio_de_Janeiro);
	    mapaEstados.put("RN", EstadoEndereco.Rio_Grande_do_Norte);
	    mapaEstados.put("RS", EstadoEndereco.Rio_Grande_do_Sul);
	    mapaEstados.put("RO", EstadoEndereco.Rondônia);
	    mapaEstados.put("RR", EstadoEndereco.Roraima);
	    mapaEstados.put("SC", EstadoEndereco.Santa_Catarina);
	    mapaEstados.put("SP", EstadoEndereco.São_Paulo);
	    mapaEstados.put("SE", EstadoEndereco.Sergipe);
	    mapaEstados.put("TO", EstadoEndereco.Tocantins);
	}
	
	 public static EstadoEndereco buscarEstadoPorSigla(String sigla) {
	        return mapaEstados.get(sigla);
	    }
	

}
