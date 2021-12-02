package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
	
	@FXML
	private Label result;
	
	private float num1 = 0;
	private String operator = "";
	private boolean start = true;
	
	Modal modal = new Modal();
			
	
	public void processNumbers(ActionEvent event) 
	{
		if(start) 
		{
			result.setText("");
			start = false;
		}
		String value = ((Button)event.getSource()).getText();
		result.setText(result.getText() + value);
	}
	
	public void processOperator(ActionEvent event) {
		String value = ((Button)event.getSource()).getText();
		
		if(!value.equals("="))
		{
			if(!operator.isEmpty())
			{
				return;
			}
			operator = value;
			num1 = Float.parseFloat(result.getText());
			result.setText("");
		}
		else 
		{
			if(operator.isEmpty())
			{
				return;
			}
			float num2 = Float.parseFloat(result.getText());
			float output = modal.calculate(num1, num2, operator);
			result.setText(String.valueOf(output));
			operator = "";
			start = true;
		}
	}
	
	public void reset() {
		result.setText("");
	}

}
