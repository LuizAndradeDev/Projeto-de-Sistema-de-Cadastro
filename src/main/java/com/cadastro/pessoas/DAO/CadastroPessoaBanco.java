package com.cadastro.pessoas.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.cadastro.endereco.modelo.EnderecoPessoa;
import com.cadastro.pessoas.modelo.PessoaModelo;
import com.cadastro.socioDemografico.modelo.BeneficioSocioDemografico;
import com.cadastro.socioDemografico.modelo.CBO;
import com.cadastro.socioDemografico.modelo.DadosSocioDemograficos;

@Stateless
public class CadastroPessoaBanco {

    @PersistenceContext
    private EntityManager manager;
    
    @Transactional
    public void cadastrarPessoa(PessoaModelo pessoa) {
        manager.persist(pessoa);
    }

    public List<PessoaModelo> listarTodasPessoas() {
        return manager.createQuery("from PessoaModelo", PessoaModelo.class).getResultList();
    }

    public PessoaModelo retornarPessoaPorID(BigDecimal id) {
        return manager.find(PessoaModelo.class, id);
    }

    public DadosSocioDemograficos retornarDadosSocioPorID(BigDecimal id) {
        return manager.find(DadosSocioDemograficos.class, id);
    }
    
    public EnderecoPessoa retornarEnderecoPorID(BigDecimal id) {
    	return manager.find(EnderecoPessoa.class, id);
    }
    
    @Transactional 
    public void excluirPessoaPorID(BigDecimal id) {
        try {
        	
        	PessoaModelo pessoa = manager.find(PessoaModelo.class, id);
        	
            if (pessoa != null) {
                EnderecoPessoa endereco = pessoa.getEnderecoPessoa();
                DadosSocioDemograficos dadosSocio = pessoa.getDadosSocioDemograficos();
                CBO cbo = dadosSocio.getCbo();
                BeneficioSocioDemografico beneficio = dadosSocio.getBeneficio();
                
                manager.remove(pessoa);
                manager.remove(endereco);
                manager.remove(dadosSocio);
                manager.remove(cbo);
                manager.remove(beneficio);
                
                System.out.println("Pessoa com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("Pessoa com ID " + id + " não encontrado. Nada foi excluído.");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao excluir Pessoa com ID " + id + ": " + ex.getMessage());
        }
    }

    public boolean verificarExistenciaCPF(String cpf) {
        TypedQuery<PessoaModelo> query = manager.createQuery(
            "SELECT d FROM PessoaModelo d WHERE d.CPF = :cpf", PessoaModelo.class);
        query.setParameter("cpf", cpf);
        List<PessoaModelo> resultados = query.getResultList();
        return !resultados.isEmpty();
    }

     
    @Transactional
    public void atualizarPessoa(PessoaModelo pessoa) {
        try {
            manager.merge(pessoa);
            manager.flush();
            System.out.println("Pessoa com ID " + pessoa.getId() + " e suas entidades relacionadas foram atualizadas com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Pessoa com ID " + pessoa.getId() + " e suas entidades relacionadas: " + e.getMessage());
        }
    }
    
    @Transactional
    public void atualizarEndereco(EnderecoPessoa endereco) {
        try {
            manager.merge(endereco);
            manager.flush();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Endereço: " + e.getMessage());
        }
    }
    
    @Transactional
    public void atualizarDadosSocio(DadosSocioDemograficos dados) {
        try {
            manager.merge(dados);
            manager.flush();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Dados Socio: " + e.getMessage());
        }
    }
}