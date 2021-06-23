package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;
	boolean flag=false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnAnalisi;

    @FXML
    private ComboBox<?> boxArco;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
			if(flag==false) {
			    		
			    		
			    		txtResult.appendText("Prima crea il grafo");
			    		
			    		return;
			    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
	txtResult.clear();

    	
    	//METTERE IL .GETVALUE()
    	String reato= boxCategoria.getValue();
    	if( reato==null) {
    		
    		txtResult.appendText("Seleziona la categoria");
    		return;
    		
    	}
    	int anno= boxAnno.getValue();
    	if(anno==0) {
    		
    		
    		txtResult.appendText("Seleziona l'anno");
    		return;
    		
    	}
    	
    	this.model.creaGrafo(anno,reato);
    	
    	txtResult.appendText("GRAFO CREATO \n");
    	txtResult.appendText("# VERTICI: "+this.model.nVertici()+"\n");
    	txtResult.appendText("# ARCHI: "+ this.model.nArchi());


		flag=true;

    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		for(String reato: this.model.getEvents()) {
			
			boxCategoria.getItems().add(reato);
			
		}
		for(int i=2014; i<=2016; i++) {
			
			boxAnno.getItems().add(i);
			
		}
	}
}
