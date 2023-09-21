package service;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.entity.Endereco;

public class GeradorDeArquivo {
	
	public void geraJson(Endereco endereco) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileWriter fw = new FileWriter("src/" + endereco.cep() + ".json");
			fw.write(gson.toJson(endereco));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
