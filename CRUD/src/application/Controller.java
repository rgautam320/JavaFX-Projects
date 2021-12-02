package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable
{
	@FXML
	private Button insertButton, updateButton, deleteButton;
	@FXML
	private TableView<Books> tv;
	@FXML
	private TextField tfID, tfTitle, tfAuthor, tfYear, tfPages;
	@FXML 
	TableColumn<Books, Integer> tvID, tvYear, tvPages;
	@FXML 
	TableColumn<Books, String> tvTitle, tvAuthor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Books> getBooksList() throws SQLException{
		ObservableList<Books> booksList = FXCollections.observableArrayList();
		
		Connection connection = DB.getConnection();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String queryString = "SELECT * FROM books";
		
		try {
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Books books = new Books(resultSet.getInt("id"), 
						resultSet.getString("title"), resultSet.getString("author"), 
						resultSet.getInt("year"), resultSet.getInt("pages"));
				
				booksList.add(books);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
		return booksList;
	}
	
	public void showBooks() throws SQLException {
		ObservableList<Books> list = getBooksList();
		
		tvID.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
		tvTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
		tvAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
		tvYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
		tvPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));

		tv.setItems(list);
	}
	
	public void onInsert() throws SQLException {
		
		Connection connection = DB.getConnection();
		String query = "INSERT INTO books values ('" + tfID.getText() + "', '" 
													+ tfTitle.getText() 
													+ "', '" + tfAuthor.getText() + "', '" 
													+ tfYear.getText() + "', '"
													+ tfPages.getText() + "')";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		showBooks();
	}
	
	public void onUpdate() throws SQLException {
		
		Connection connection = DB.getConnection();
		String queryString = "UPDATE books SET title = ?, author = ?, year = ?, pages = ? WHERE id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
		preparedStatement.setString(1, tfTitle.getText());
		preparedStatement.setString(2, tfAuthor.getText());
		preparedStatement.setString(3, tfYear.getText());
		preparedStatement.setString(4, tfPages.getText());
		preparedStatement.setString(5, tfID.getText());
		
		preparedStatement.executeUpdate();
		
		showBooks();
	}
	
	public void onDelete() throws SQLException {
		Connection connection = DB.getConnection();
		String queryString = "DELETE FROM books WHERE id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(queryString);
		preparedStatement.setString(1, tfID.getText());
		
		preparedStatement.executeUpdate();
		
		showBooks();
	}
	
	public void handleMouseAction() {
		try {
			Books books = tv.getSelectionModel().getSelectedItem();
			tfID.setText(Integer.toString(books.getId()));
			tfTitle.setText(books.getTitle());
			tfAuthor.setText(books.getAuthor());
			tfYear.setText(Integer.toString(books.getYear()));
			tfPages.setText(Integer.toString(books.getPages()));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
