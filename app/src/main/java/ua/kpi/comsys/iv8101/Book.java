package ua.kpi.comsys.iv8101;

public class Book {
    private final String title;
    private final String subtitle;
    private final String isbn13;
    private final String price;
    private final int image;

    public Book(String title, String subtitle, String isbn13, String price, int image) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.image = image;
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
        return isbn13;
    }
    public int getImageID(){
        return image;
    }
}
