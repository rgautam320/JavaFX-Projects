package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Controller implements Initializable {
	
	@FXML
	private WebView myWebView;
	@FXML
	private TextField myTextField;
	
	private WebEngine engine;
	private WebHistory history;
	private double webZoom = 1.0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine = myWebView.getEngine();
		engine.load("https://www.google.com");
	}
	
	public void Search() {
		String urlString = myTextField.getText();
		urlString = "https://" + urlString;
		engine.load(urlString);
	}
	public void home() {
		engine.load("https://www.google.com");
	}
	
	public void reload() {
		engine.reload();
	}
	public void zoomIn() {
		webZoom += 0.25;
		myWebView.setZoom(webZoom);
	}
	public void zoomOut() {
		webZoom -= 0.25;
		myWebView.setZoom(webZoom);
	}
	public void back() {
		history = engine.getHistory();
		history.go(-1);
	}
	public void forward() {
		history = engine.getHistory();
		history.go(+1);
	}

}
