package com.cadastro.socioDemografico.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CBO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private BigDecimal id;

	@Column(name = "nome_CBO", length = 50, nullable = false)
	private String nome;

	@Column(name = "codigo_CBO", length = 7, nullable = false)
	private String codigo;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

}
