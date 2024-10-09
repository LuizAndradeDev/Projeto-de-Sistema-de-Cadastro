package com.cadastro.teste;

import javax.enterprise.inject.Model;

import com.cadastro.pessoas.modelo.PessoaModelo;

@Model
public class TesteBean {
	PessoaModelo pessoa = new PessoaModelo();

	public PessoaModelo getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaModelo pessoa) {
		this.pessoa = pessoa;
	}
	
	public void nomeMostrar() {
		System.out.println(pessoa.getNome());
	}
	

}
