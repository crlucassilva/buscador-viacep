package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.entity.Endereco;
import service.ConsultaCEP;
import service.GeradorDeArquivo;

public class MainViewController implements Initializable {

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
		System.out.println("onMenuItemAboutAction");
	}
	
	public void onMenuItemExportarAction() {
		System.out.println("onMenuItemExportAction");
	}
	
	public void onBtConsultarAction() {
		ConsultaCEP consulta = new ConsultaCEP();
		try {
			Endereco novoEndereco = consulta.buscaEndereco(txtCEP.getText());
			preencher(novoEndereco);
			GeradorDeArquivo gerador = new GeradorDeArquivo();
			gerador.geraJson(novoEndereco);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
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
	}
}
