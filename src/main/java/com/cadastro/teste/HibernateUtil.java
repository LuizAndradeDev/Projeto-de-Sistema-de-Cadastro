package com.cadastro.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HibernateUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/cadastro";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "vhs97";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}