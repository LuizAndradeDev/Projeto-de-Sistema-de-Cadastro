package com.cadastro.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cadastro");
	
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}