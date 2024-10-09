package com.cadastro.endereco.modelo.enums;

public enum EstadoEndereco {
	Acre("Acre"),
	Alagoas("Alagoas"),
	Amapá("Amapá"),
	Amazonas("Amazonas"), 
	Bahia("Bahia"),
	Ceará("Ceará"),
	Distrito_Federal("Distrito Federal"),
	Espírito_Santo("Espírito Santo"),
	Goiás("Goiás"),
	Maranhão("Maranhão"),
	Mato_Grosso("Mato Grosso"),
	Mato_Grosso_do_Sul("Mato Grosso do Sul"),
	Minas_Gerais("Minas Gerais"),
	Pará("Pará"),
	Paraíba("Paraíba"),
	Paraná("Paraná"),
	Pernambuco("Pernambuco"),
	Piauí("Piauí"),
	Rio_de_Janeiro("Rio de Janeiro"),
	Rio_Grande_do_Norte("Rio Grande do Norte"),
	Rio_Grande_do_Sul("Rio Grande do Sul"),
	Rondônia("Rondônia"),
	Roraima("Roraima"),
	Santa_Catarina("Santa Catarina"),
	São_Paulo("São Paulo"),
	Sergipe("Sergipe"),
	Tocantins("Tocantins"); 
	
	private String descricao;
	
	EstadoEndereco (String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
