package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import model.entity.Endereco;

public class ConsultaCEP {
	
	public Endereco buscaEndereco(String cep) {
		
		String endereco = "https://viacep.com.br/ws/" + cep + "/json/";
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco))
				.build();
		
		try {
			HttpResponse<String> response = client
					.send(request, HttpResponse.BodyHandlers.ofString());
			return new Gson().fromJson(response.body(), Endereco.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
