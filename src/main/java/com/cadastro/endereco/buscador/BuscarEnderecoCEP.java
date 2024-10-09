package com.cadastro.endereco.buscador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.cadastro.endereco.conversor.ConversorSiglaPraEnum;
import com.cadastro.endereco.modelo.EnderecoPessoa;
import com.google.gson.JsonObject;

public class BuscarEnderecoCEP {

	private static final String[] TIPOS_LOGRADOURO = { "Rua", "Avenida", "Travessa", "Praça", "Alameda", "Estrada",
			"Rodovia" };

	public static String extrairTipoLogradouro(String logradouroCompleto) {
		for (String tipo : TIPOS_LOGRADOURO) {
			if (logradouroCompleto.toLowerCase().startsWith(tipo.toLowerCase())) {
				return tipo;
			}
		}
		return "desconhecido";
	}

	public static EnderecoPessoa consultarCEP(String cep) {
		EnderecoPessoa endereco = new EnderecoPessoa();

		try {
			URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Parse JSON response and populate endereco object
				String jsonResponse = response.toString();
				if (!jsonResponse.isEmpty()) {
					if (isCEPValid(jsonResponse)) {
						parseJsonResponse(jsonResponse, endereco);
					} else {
						endereco = null;
						System.out.println("CEP inválido: " + cep);
					}
				} else {
					System.out.println("Resposta vazia da API");
				}
			} else {
				System.out.println("Falha na requisição. Código de resposta: " + responseCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return endereco;
	}

	private static boolean isCEPValid(String jsonResponse) {
		// Parse JSON to check if the response contains "erro: true"
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
		if (jsonObject.has("erro") && jsonObject.get("erro").getAsBoolean()) {
			return false; // CEP inválido
		}
		return true; // CEP válido
	}

	private static void parseJsonResponse(String jsonResponse, EnderecoPessoa endereco) {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

		endereco.setLogradouro(jsonObject.get("logradouro").getAsString());
		endereco.setBairro(jsonObject.get("bairro").getAsString());
		endereco.setCidade(jsonObject.get("localidade").getAsString());
		endereco.setTipoDeLogradouro(extrairTipoLogradouro(jsonObject.get("logradouro").getAsString()));
		String ufSigla = jsonObject.get("uf").getAsString();
		endereco.setEstadoUF(ConversorSiglaPraEnum.buscarEstadoPorSigla(ufSigla));
		if (endereco.getEstadoUF() != null) {
			endereco.setEstadoUF(ConversorSiglaPraEnum.buscarEstadoPorSigla(ufSigla));
		} else {
			System.out.println("Estado não encontrado para a sigla: " + ufSigla);
		}

	}
}
