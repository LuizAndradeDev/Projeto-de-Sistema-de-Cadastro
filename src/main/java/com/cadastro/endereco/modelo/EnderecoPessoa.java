package com.cadastro.endereco.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cadastro.endereco.modelo.enums.EstadoEndereco;

@Entity
public class EnderecoPessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private BigDecimal id;

	@Column(name = "logradouroEndereco", length = 50, nullable = false)
	private String logradouro;

	@Column(name = "tipoLogradouroEndereco", length = 50, nullable = false)
	private String tipoDeLogradouro;

	@Column(name = "numeroEndereco", length = 15, nullable = false)
	private String numero;

	@Column(name = "CEPEndereco", length = 15, nullable = false)
	private String CEP;

	@Column(name = "bairroEndereco", length = 50, nullable = false)
	private String bairro;

	@Column(name = "cidadeEndereco", length = 30, nullable = false)
	private String cidade;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_endereco", length = 18, nullable = false)
	private EstadoEndereco estadoUF;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getTipoDeLogradouro() {
		return tipoDeLogradouro;
	}

	public void setTipoDeLogradouro(String tipoDeLogradouro) {
		this.tipoDeLogradouro = tipoDeLogradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EstadoEndereco getEstadoUF() {
		return estadoUF;
	}

	public void setEstadoUF(EstadoEndereco estadoUF) {
		this.estadoUF = estadoUF;
	}

}
