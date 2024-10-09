package com.cadastro.pessoas.modelo;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


import com.cadastro.endereco.modelo.EnderecoPessoa;
import com.cadastro.pessoas.modelo.enums.Raca;
import com.cadastro.pessoas.modelo.enums.Sexo;
import com.cadastro.socioDemografico.modelo.DadosSocioDemograficos;

@Entity
public class PessoaModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private BigDecimal id;

	@Column(name = "nomePessoa", length = 100, nullable = false)
	private String nome;

	@Column(name = "rg", length = 20, nullable = false)
	private String RG;

	@Column(name = "cpf", length = 20, nullable = false)
	private String CPF;

	@Column(name = "dataDeNascimentoPessoa", nullable = false)
	private Date dataDeNascimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "sexoPessoa", length = 10, nullable = false)
	private Sexo sexo;

	@Enumerated(EnumType.STRING)
	@Column(name = "racaPessoa", length = 10, nullable = false)
	private Raca raca;
	
	@Column(name = "telefone1Pessoa", length = 15, nullable = false)
	private String telefone1;
	
	@Column(name = "telefone2Pessoa", length = 15, nullable = true)
	private String telefone2;
	
	@Column(name = "emailPessoa", length = 255, nullable = false)
	private String email;
	
	@OneToOne
	private EnderecoPessoa enderecoPessoa;
	
	@OneToOne
	private DadosSocioDemograficos dadosSocioDemograficos;

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

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public EnderecoPessoa getEnderecoPessoa() {
		return enderecoPessoa;
	}

	public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DadosSocioDemograficos getDadosSocioDemograficos() {
		return dadosSocioDemograficos;
	}

	public void setDadosSocioDemograficos(DadosSocioDemograficos dadosSocioDemograficos) {
		this.dadosSocioDemograficos = dadosSocioDemograficos;
	}

}
