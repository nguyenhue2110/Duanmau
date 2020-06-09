package huentps08729.com.myapplication.model;

public class TheLoai {

    private String Name;
    private String Image;
    private String MoTa;
    private int ViTri;

    public TheLoai() {
    }

    public TheLoai(String name, String image, String moTa, int viTri) {
        Name = name;
        Image = image;
        MoTa = moTa;
        ViTri = viTri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getViTri() {
        return ViTri;
    }

    public void setViTri(int viTri) {
        ViTri = viTri;
    }
}
