package com.cadastro.teste;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cadastro.pessoas.modelo.PessoaModelo;
import com.cadastro.pessoas.modelo.enums.Sexo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class Testerelatorio {

	public static void main(String[] args) throws SQLException, JRException, FileNotFoundException {
		Map<String, Object> params = new HashMap<String, Object>();

		//JasperCompileManager.compileReportToFile("src/main/webapp/report3.jrxml");
		try (Connection connection = HibernateUtil.getConnection()) {
			PessoaModelo pessoa = new PessoaModelo();
			pessoa.setSexo(Sexo.Masculino);
			params.put("sexopessoa",pessoa.getSexo());
			JasperPrint jasperPrint =  JasperFillManager.fillReport("src/main/webapp/WEB-INF/jasper/report3.jasper", params, connection);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("relatorio.pdf"));
			exporter.exportReport();
		}
	}

}
