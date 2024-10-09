package com.cadastro.socioDemografico.modelo.enums;

public enum GrauDeIntrucao {
	
	Superior_Completo("Superior Completo"),
	Superior_Incompleto("Superior Incompleto"),
	Ensino_Medio_Completo("Ensino Medio Completo"),
	Ensino_Medio_Incompleto("Ensino Medio Incompleto"),
	Ensino_Fundamental_Completo("Ensino Fundamental Completo"),
	Ensino_Fundamental_Incompleto("Ensino Fundamental Incompleto"),
	Lê_e_Escreve("Lê e Escreve"),
	Analfabeto("Analfabeto");
	
    private String descricao;
	
    GrauDeIntrucao (String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
