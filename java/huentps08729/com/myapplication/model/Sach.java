package huentps08729.com.myapplication.model;

public class Sach {
    private String bookid;
    private String imgSach;
    private String name;
    private String tenTL;
    private String tenNXB;
    private String tenTG;
    private int gia;
    private String baove;

    public Sach() {
    }

    public Sach(String bookid, String imgSach, String name, String tenTL, String tenNXB, String tenTG, int gia, String baove) {
        this.bookid = bookid;
        this.imgSach = imgSach;
        this.name = name;
        this.tenTL = tenTL;
        this.tenNXB = tenNXB;
        this.tenTG = tenTG;
        this.gia = gia;
        this.baove = baove;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getImgSach() {
        return imgSach;
    }

    public void setImgSach(String imgSach) {
        this.imgSach = imgSach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getBaove() {
        return baove;
    }

    public void setBaove(String baove) {
        this.baove = baove;
    }
}
