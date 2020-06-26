package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<String> cmbBoxStati;

    @FXML
    private Button btnVisualizzaVelivoli;

    @FXML
    private TextField txtT;

    @FXML
    private TextField txtG;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	model.creaGrafo();
    	cmbBoxStati.getItems().addAll(model.getStati());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String s = cmbBoxStati.getValue();
    	if(s==null) {
    		txtResult.appendText("Selezionare uno stato dal menu a tendina.\n");
    		return;
    	} else {
    			try {
    				
    				Integer T = Integer.parseInt(txtT.getText());
    				Integer G = Integer.parseInt(txtG.getText());
    				Map<String, Integer> mappa = model.doSimulazione(s, T, G);
    				if(mappa!=null) {
    					for(String res : mappa.keySet()) {
    						txtResult.appendText(res+" "+mappa.get(res)+"\n");
    					}
    				} else {
    					txtResult.appendText("Errore.\n");
    				}
    			
    			} catch(NumberFormatException e) {
    				e.printStackTrace();
    				txtResult.appendText("Scrivi un numero intero.\n");
    		}
    	}
    }

    @FXML
    void doVisualizzaVelivoli(ActionEvent event) {
    	String s = cmbBoxStati.getValue();
    	if(s==null) {
    		txtResult.appendText("Selezionare uno stato dal menu a tendina.\n");
    		return;
    	} else {
    		List<Adiacenza> adiacenze = model.getVicini(s);
    		if(s!=null) {
    			txtResult.appendText("VICINI: \n");
    			for(Adiacenza a : adiacenze) {
    				txtResult.appendText(a.toString()+"\n");
    			}
    		}
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnVisualizzaVelivoli != null : "fx:id=\"btnVisualizzaVelivoli\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtT != null : "fx:id=\"txtT\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtG != null : "fx:id=\"txtG\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
