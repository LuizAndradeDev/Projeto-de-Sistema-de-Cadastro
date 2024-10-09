package com.cadastro.pessoas.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cadastro.pessoas.modelo.enums.Nivel;

@Entity
public class UsuarioModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private BigDecimal id;

	@Column(name = "nomeUsuario", length = 100, nullable = false)
	private String nome;

	@Column(name = "emailUsuario", length = 50, nullable = false)
	private String email;

	@Column(name = "senhaUsuario", length = 50, nullable = false)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nivelUsuario", length = 30, nullable = false)
	private Nivel nivel;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	

}
