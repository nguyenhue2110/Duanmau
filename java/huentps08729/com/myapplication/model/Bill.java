package huentps08729.com.myapplication.model;

public class Bill {
  private   String billId;
    private String billName;
    private String date;

    public Bill() {
    }

    public Bill(String billId, String billName, String date) {
        this.billId = billId;
        this.billName = billName;
        this.date = date;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
