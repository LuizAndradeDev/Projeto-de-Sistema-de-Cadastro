package com.cadastro.socioDemografico.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.cadastro.socioDemografico.modelo.enums.GrauDeIntrucao;

@Entity
public class DadosSocioDemograficos {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private BigDecimal id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "grau_de_instrucao_dados_socio", length = 50, nullable = false)
	private GrauDeIntrucao grauDeInstrucao;
	
	@OneToOne
	private CBO cbo;
	
	@OneToOne
	private BeneficioSocioDemografico beneficio;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public CBO getCbo() {
		return cbo;
	}
	public void setCbo(CBO cbo) {
		this.cbo = cbo;
	}
	public BeneficioSocioDemografico getBeneficio() {
		return beneficio;
	}
	public void setBeneficio(BeneficioSocioDemografico beneficio) {
		this.beneficio = beneficio;
	}
	public GrauDeIntrucao getGrauDeInstrucao() {
		return grauDeInstrucao;
	}
	public void setGrauDeInstrucao(GrauDeIntrucao grauDeInstrucao) {
		this.grauDeInstrucao = grauDeInstrucao;
	}
	
	

}
