/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnGrassi;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Vertex> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer porzioni = -1;
    	try {
    		porzioni = Integer.parseInt(txtPorzioni.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("inserisci un numero naturale");
    		return;
    	}
    	if(porzioni<0) {
    		txtResult.appendText("inserisci un numero naturale");
    		return;
    	}
    	this.model.creaGrafo(porzioni);
    	txtResult.appendText("vertici: "+this.model.getGrafo().vertexSet().size()+"\n");
    	txtResult.appendText("archi: "+this.model.getGrafo().edgeSet().size()+"\n");
    	boxFood.getItems().clear();
    	boxFood.getItems().addAll(this.model.getGrafo().vertexSet());
    	btnGrassi.setDisable(false);
    	btnSimula.setDisable(false);
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	Vertex v = boxFood.getValue();
    	if(v == null) {
    		txtResult.appendText("scegli un cibo");
    		return;
    	}
    	int i = 1;
    	for(DefaultWeightedEdge e : this.model.grassi(v)) {
    		if(i<=5) {
    			txtResult.appendText(this.model.getGrafo().getEdgeTarget(e)+" "+this.model.getGrafo().getEdgeWeight(e)+"\n");
    			i++;
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Integer K = -1;
    	try {
    		K = Integer.parseInt(txtK.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("inserisci un numero tra 1 e 10");
    		return;
    	}
    	if(K<1 || K>10) {
    		txtResult.appendText("inserisci un numero tra 1 e 10");
    		return;
    	}
    	Vertex v = boxFood.getValue();
    	if(v == null) {
    		txtResult.appendText("scegli un cibo");
    		return;
    	}
    	this.model.getSim().init(this.model, K, v);
    	this.model.getSim().run();
    	txtResult.appendText("cibi preparati: "+this.model.getSim().getCibiPreparati()+"\n");
    	txtResult.appendText("tempo totale: "+this.model.getSim().getTotalTime()+"\n");
    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	btnGrassi.setDisable(true);
    	btnSimula.setDisable(true);
    }
}
    
    
