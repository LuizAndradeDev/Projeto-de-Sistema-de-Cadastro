package com.cadastro.socioDemografico.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.cadastro.socioDemografico.modelo.DadosSocioDemograficos;
import com.cadastro.socioDemografico.modelo.BeneficioSocioDemografico;
import com.cadastro.socioDemografico.modelo.CBO;

@Stateless
public class CadastroSocioDemograficoBanco {

    @PersistenceContext
    private EntityManager manager;
    
    @Transactional 
    public void cadastrarDadosSocio(DadosSocioDemograficos dadosSocio) {
        try {
            manager.persist(dadosSocio);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar Dados Socio: " + e.getMessage());
        }
    }
    
    @Transactional 
    public void cadastrarBeneficio(BeneficioSocioDemografico beneficioSocio) {
        try {
            manager.persist(beneficioSocio);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar Benefício: " + e.getMessage());
        }
    }
    
    @Transactional 
    public void cadastrarCBO(CBO dadosCBO) {
        try {
            manager.persist(dadosCBO);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar CBO: " + e.getMessage());
        }
    }

    public CBO retornarCBOPorID(BigDecimal id) {
        TypedQuery<CBO> query = manager.createQuery("select u from CBO u where u.id = :pid", CBO.class);
        query.setParameter("pid", id);
        List<CBO> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            System.out.println("CBO com id não encontrado.");
            return null;
        }
    }
    
    
    public BeneficioSocioDemografico retornarBenficioPorID(BigDecimal id) {
        TypedQuery<BeneficioSocioDemografico> query = manager.createQuery("select u from BeneficioSocioDemografico u where u.id = :pid", BeneficioSocioDemografico.class);
        query.setParameter("pid", id);
        List<BeneficioSocioDemografico> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            System.out.println("Benefício com id não encontrado.");
            return null;
        }
    }
    
    @Transactional 
    public void excluirBeneficioPorID(BigDecimal id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            BeneficioSocioDemografico beneficio = manager.find(BeneficioSocioDemografico.class, id);

            if (beneficio != null) {
                manager.remove(beneficio);
                System.out.println("Benefício com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("Benefício com ID " + id + " não encontrado. Nada foi excluído.");
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir Benefício com ID " + id + ": " + ex.getMessage());
        }
    }
    
    @Transactional 
    public void excluirCBOPorID(BigDecimal id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            CBO cbo = manager.find(CBO.class, id);

            if (cbo != null) {
                manager.remove(cbo);
                System.out.println("CBO com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("CBO com ID " + id + " não encontrado. Nada foi excluído.");
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir CBO com ID " + id + ": " + ex.getMessage());
        }
    }
   
    @Transactional 
    public void excluirDadosSocioPorID(BigDecimal id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            DadosSocioDemograficos dadosSocioDemograficos = manager.find(DadosSocioDemograficos.class, id);

            if (dadosSocioDemograficos != null) {
                manager.remove(dadosSocioDemograficos);
                System.out.println("Dados Socio com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("Dados Socio com ID " + id + " não encontrado. Nada foi excluído.");
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir Dados Socio com ID " + id + ": " + ex.getMessage());
        }
    }

    @Transactional
    public void atualizarCBO(CBO cbo) {
        try {
            manager.merge(cbo);
            manager.flush();
            System.out.println("CBO atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar CBO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void atualizarBeneficio(BeneficioSocioDemografico beneficio) {
        try {
            manager.merge(beneficio);
            manager.flush();
            System.out.println("Benefício atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar Benefício: " + e.getMessage());
            e.printStackTrace();
        }
    }
}