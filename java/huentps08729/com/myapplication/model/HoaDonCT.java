package huentps08729.com.myapplication.model;

public class HoaDonCT {
    private String detailsid;
    private String idbook;
    private String date;
    private int quantity;
    private int price;
    private int total;

    public HoaDonCT() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HoaDonCT(String detailsid, String idbook, String date, int quantity, int price, int total) {
        this.detailsid = detailsid;
        this.idbook = idbook;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public String getDetailsid() {
        return detailsid;
    }

    public void setDetailsid(String detailsid) {
        this.detailsid = detailsid;
    }

    public String getIdbook() {
        return idbook;
    }

    public void setIdbook(String idbook) {
        this.idbook = idbook;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
