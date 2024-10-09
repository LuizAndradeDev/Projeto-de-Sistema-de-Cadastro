package com.cadastro.pessoa.CPF.validador;

public class validacaoCPF {
	public static boolean metodoValidarCPF(String cpf) {
 
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
 
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
 
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int remainder = 11 - (sum % 11);
        int firstVerifier = (remainder >= 10) ? 0 : remainder;
        if (Character.getNumericValue(cpf.charAt(9)) != firstVerifier) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        remainder = 11 - (sum % 11);
        int secondVerifier = (remainder >= 10) ? 0 : remainder;
        if (Character.getNumericValue(cpf.charAt(10)) != secondVerifier) {
            return false;
        }
        return true;
    }

}
