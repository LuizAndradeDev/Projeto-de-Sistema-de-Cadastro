package com.cadastro.teste;


public class teste {
	
private static final String[] TIPOS_LOGRADOURO = {"rua", "avenida", "travessa", "praça", "alameda", "estrada", "rodovia"};
    
    
    public static String extrairTipoLogradouro(String logradouroCompleto) {
        for (String tipo : TIPOS_LOGRADOURO) {
            if (logradouroCompleto.toLowerCase().startsWith(tipo.toLowerCase())) {
                return tipo;
            }
        }
        return "desconhecido"; // ou null, dependendo do seu requisito
    }

    public static void main(String[] args) {
        String logradouroCompleto1 = "rua paulo avelino neto";
        String logradouroCompleto2 = "avenida brasil";
        
        System.out.println("oi");
        System.out.println(extrairTipoLogradouro(logradouroCompleto1)); // saída: rua
        System.out.println(extrairTipoLogradouro(logradouroCompleto2)); // saída: avenida
    }
}