package application;

public class Modal {
	
	public float calculate(float num1, float num2, String operator)
	{
		switch (operator) {
		case "+":
			return num1 + num2;
		case "-":
			return num1 - num2;
		case "*":
			return num1 * num2;
		case "/":
			if(num2 == 0)
			{
				return 0;
			} 
			else 
			{
				return num1 / num2;
			}
			
		default:
			return 0;
		}
	}

}
