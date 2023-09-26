package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import gui.utils.Alerts;
import gui.utils.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.entity.Endereco;
import model.exceptions.ValidationException;
import service.ConsultaCEP;
import service.GeradorDeArquivo;

public class MainViewController implements Initializable {
	
	private Endereco endereco;
	
	private Map<String, Endereco> enderecos = new HashMap<>();

	@FXML
	private MenuItem menuItemConsultar;
	
	@FXML
	private MenuItem menuItemExportar;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	private TextField txtCEP;
	
	@FXML
	private TextField txtLogradouro;
	
	@FXML
	private TextField txtBairro;
	
	@FXML
	private TextField txtLocalidade;
	
	@FXML
	private TextField txtEstado;
	
	@FXML
	private Button btConsultar;
	
	public void onMenuItemSobreAction() {
		System.out.println("onMenuItemSobreAction");
	}
	
	public void onMenuItemExportarAction() {
		GeradorDeArquivo gerador = new GeradorDeArquivo();
		gerador.geraJson(endereco);
		Alerts.showAlert("Exportar Json", null, "Arquivo foi exportado para ../src/", AlertType.INFORMATION);
	}
	
	public void onBtConsultarAction() {
		try {
			preencher(validaEndereco());
		} catch (ValidationException e) {
			Alerts.showAlert("Erro", null, e.getMessage(), Alert.AlertType.ERROR);
		} catch (RuntimeException e) {
			Alerts.showAlert("Erro", "Erro inesperado. Tente novamente mais tarde.", e.getMessage(), Alert.AlertType.ERROR);
		}
	}
	
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

	private void preencher(Endereco endereco) {
				txtLogradouro.setText(endereco.logradouro());
				txtBairro.setText(endereco.bairro());
				txtLocalidade.setText(endereco.localidade());
				txtEstado.setText(endereco.uf());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtCEP);
		Constraints.setTextFieldMaxLength(txtCEP, 8);
	}
}
