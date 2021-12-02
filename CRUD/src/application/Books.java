package application;

public class Books 
{
	private int id;
	private String title;
	private String author;
	private int year;
	private int pages;
	public Books(int id, String title, String author, int year, int pages) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.pages = pages;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public int getYear() {
		return year;
	}
	public int getPages() {
		return pages;
	}
}
