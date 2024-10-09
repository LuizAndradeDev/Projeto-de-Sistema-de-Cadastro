package com.cadastro.pessoas.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.cadastro.pessoas.modelo.UsuarioModelo;

@Stateless
public class UsuarioDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
    public void cadastrarUsuario(UsuarioModelo usuario) {
        manager.persist(usuario);
    }
	
	@Transactional
	public UsuarioModelo retornarUsuarioPorID(BigDecimal id) {
        return manager.find(UsuarioModelo.class, id);
    }
	
	@Transactional
	public void excluirUsuarioPorID(BigDecimal id) {
        try {
        	
        	UsuarioModelo usuario = manager.find(UsuarioModelo.class, id);
        	
            if (usuario != null) {
                
                manager.remove(usuario);
      
                
                System.out.println("Usuario com ID " + id + " foi excluído com sucesso.");
            } else {
                System.out.println("Usuario com ID " + id + " não encontrado. Nada foi excluído.");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao excluir Usuariocom ID " + id + ": " + ex.getMessage());
        }
    }
	
	 @Transactional
	    public void atualizarUsuario(UsuarioModelo usuario) {
	        try {
	            manager.merge(usuario);
	            manager.flush();
	            System.out.println("Usuario com ID " + usuario.getId() + " e suas entidades relacionadas foram atualizadas com sucesso.");
	        } catch (Exception e) {
	            System.out.println("Erro ao atualizar Usuario com ID " + usuario.getId() + " e suas entidades relacionadas: " + e.getMessage());
	        }
	    }
	 
	 @Transactional
	    public boolean existe(UsuarioModelo usuario) {
	        String jpql = "select u from UsuarioModelo u where u.email = :pEmail and u.senha = :pSenha";
	        
	        try {
	           manager.createQuery(jpql, UsuarioModelo.class)
	              .setParameter("pEmail", usuario.getEmail())
	              .setParameter("pSenha", usuario.getSenha())
	              .getSingleResult();
	            return true;
	        } catch (NoResultException ex) {
	            return false;
	        }
	    }
	 
	 @Transactional
	    public UsuarioModelo retornarUsuarioPorEmail(String email) {
	        String jpql = "select u from UsuarioModelo u where u.email = :pEmail";
	        TypedQuery<UsuarioModelo> query = manager.createQuery(jpql, UsuarioModelo.class);
	        query.setParameter("pEmail", email);
	        
	        List<UsuarioModelo> resultados = query.getResultList();
	        
	        if (!resultados.isEmpty()) {
	            return resultados.get(0);
	        } else {
	            System.out.println("Usuário com o email especificado não encontrado.");
	            return null;
	        }
	    }
	 
	 @Transactional
	    public boolean verificarExistenciaEmail(String email) {
	        TypedQuery<UsuarioModelo> query = manager.createQuery(
	            "SELECT u FROM UsuarioModelo u WHERE u.email = :email", UsuarioModelo.class);
	        query.setParameter("email", email);
	        List<UsuarioModelo> resultados = query.getResultList();
	        return !resultados.isEmpty();
	    }
	 
	 @Transactional
	 public List<UsuarioModelo> listarTodos() {
		    TypedQuery<UsuarioModelo> query = manager.createQuery("SELECT u FROM UsuarioModelo u", UsuarioModelo.class);
		    return query.getResultList();
		}
	 

}
