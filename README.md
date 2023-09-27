# Buscador viaCep
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/crlucassilva/buscador-viacep/blob/main/LICENSE)
> Status: Finalizado!

# Sobre o Projeto

Essa aplicação JavaFX permite ao usuário consultar o endereço de um CEP a partir de uma API externa. O endereço pode ser exportado para um arquivo JSON.   

## Quais são os objetivos

- Desenvolver uma aplicação JavaFX
- Requisitar API viaCEP
- Validar Endereço
- Exportar arquivo JSON

## Tecnologias utilizadas

- Java
- JavaFX
- Scene Builder
- API viaCEP

## API viaCEP
A API viaCEP é uma API REST que fornece informações de endereço a partir de um CEP. A API é gratuita para uso pessoal e comercial. 

Requisitando API:
```java
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
```

## Validação
A aplicação verifica a validade do CEP de acordo com a regra adotada, se for válido, verifica se o CEP já foi pesquisado e armazenado em um mapa antes de fazer uma chamada à API:
```java
private Endereco validaEndereco() {
		if (enderecos.containsKey(txtCEP.getText())) {
			return endereco = enderecos.get(txtCEP.getText());
		} else {
			ConsultaCEP consulta = new ConsultaCEP();
			
			if (txtCEP.getText() == null || txtCEP.getText().trim().equals("") || txtCEP.getLength() != 8) {
				throw new ValidationException("CEP Inválido!");
			}
			endereco = consulta.buscaEndereco(txtCEP.getText());
			enderecos.put(endereco.cep().replace("-", ""), endereco);
			return endereco;
		}
	}
```

## Exportar arquivo
Somente após executar a consulta, é possível exportar o endereço para um arquivo JSON. O arquivo "cep.json" será criado na pasta definida pelo projeto:
```java
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
```

O conteúdo do arquivo será o seguinte:
```
{
  "cep": "01510-000",
  "logradouro": "Rua da Glória",
  "bairro": "Liberdade",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

## Aplicativo em funcionamento

https://github.com/crlucassilva/buscador-viacep/assets/74364754/a375f32b-dfb3-4b36-ac35-f28d204a4e3c

