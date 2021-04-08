package ua.kpi.comsys.iv8101.ui.books;

public class Book {
    private final String title;
    private final String subtitle;
    private final String isbn13;
    private final String price;
    private final String imageSRC;

    private String authors;
    private String publisher;
    private String pages;
    private String year;
    private String rating;
    private String desc;

    private boolean created = false;

    public Book(String title, String subtitle, String isbn13, String price, String image) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.imageSRC = image;
    }

    public Book(String title, String subtitle, String price, boolean created) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = "";
        this.price = price;
        this.imageSRC = null;
        this.created = created;
    }

    public void setInfo(String authors, String publisher, String pages, String year, String rating, String description){
        this.authors = authors;
        this.publisher = publisher;
        this.pages = pages;
        this.year = year;
        this.rating = rating;
        this.desc = description;
    }

    public String getTitle() {
        return title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public String getPrice() {
        return price;
    }
    public String getIsbn13() {
        return "i" + isbn13;
    }
    public String getImageSRC(){
        return imageSRC;
    }
    public String getAuthors() {
        return authors;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getPages() {
        return pages;
    }
    public String getYear() {
        return year;
    }
    public String getRating() {
        return rating;
    }
    public String getDesc() {
        return desc;
    }

    public String getInfo() {
        return "<font color=#888888>Title: </font> " + title + "<br> " +
                "<font color=#888888>Subtitle: </font>" + subtitle + "<br>" +
                "<font color=#888888>Description: </font>" + desc + "<br><br>" +
                "<font color=#888888>Authors: </font> " + authors + "<br>" +
                "<font color=#888888>Publisher: </font> " + publisher + "<br><br> " +
                "<font color=#888888>Pages: </font> " + pages + "<br> " +
                "<font color=#888888>Year: </font> " + year + "<br> " +
                "<font color=#888888>Rating: </font> " + rating + "<br>";
    }

    public boolean isCreated(){
        return created;
    }
}