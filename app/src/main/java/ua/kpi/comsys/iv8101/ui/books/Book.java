package ua.kpi.comsys.iv8101.ui.books;

public class Book {
    private final String title;
    private final String subtitle;
    private final String isbn13;
    private final String price;
    private final int image;

    private String authors;
    private String publisher;
    private String pages;
    private String year;
    private String rating;
    private String desc;

    public Book(String title, String subtitle, String isbn13, String price, int image) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.image = image;
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
    public int getImageID(){
        return image;
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
}