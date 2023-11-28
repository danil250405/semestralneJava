package AllClasses;

public class Book {
    boolean opportunityToBorrowABook;
   private String bookName;
   private int bookId;
    private String bookAuthor;
    private int bookYear;
    private int iduser;
    private String location;

    public Book(int bookId, String bookName,  String bookAuthor, int bookYear, String location, boolean opportunityToBorrowABook) {
        this.bookName = bookName;
        this.bookId = bookId;
        this.bookAuthor = bookAuthor;
        this.bookYear = bookYear;
        this.location = location;
        this.opportunityToBorrowABook = opportunityToBorrowABook;
    }

    public boolean isOpportunityToBorrowABook() {
        return opportunityToBorrowABook;
    }

    public void setOpportunityToBorrowABook(boolean opportunityToBorrowABook) {
        this.opportunityToBorrowABook = opportunityToBorrowABook;
    }

    public Book(String bookName, String bookAuthor, int bookYear, int iduser) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookYear = bookYear;
        this.iduser = iduser;
    }
    public Book(){}
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
