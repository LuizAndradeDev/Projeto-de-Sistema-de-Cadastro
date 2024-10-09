package com.cadastro.endereco.DAO;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.persistence.EntityTransaction;

import com.cadastro.endereco.modelo.EnderecoPessoa;

@Stateless
public class CadastroEnderecoBanco {

    @PersistenceContext
    private EntityManager manager;
    
    @Transactional 
    public void cadastrarEndereco(EnderecoPessoa endereco) {
        try {
            manager.persist(endereco);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar Endereço: " + e.getMessage());
        }
    }
    
    @Transactional 
    public void excluirEnderecoPorID(BigDecimal id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            EnderecoPessoa enderecoPessoa = manager.find(EnderecoPessoa.class, id);

            if (enderecoPessoa != null) {
                manager.remove(enderecoPessoa);
                System.out.println("Endereço com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("Endereço com ID " + id + " não encontrado. Nada foi excluído.");
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir Endereço com ID " + id + ": " + ex.getMessage());
        }
    }
}