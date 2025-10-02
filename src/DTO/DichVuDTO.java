package DTO;

public class DichVuDTO {
    private int madv;
    private String tendv;
    private String mota;
    private double gia;

    // Constructors
    public DichVuDTO() {}
    public DichVuDTO(int madv, String tendv, String mota, double gia) {
        this.madv = madv;
        this.tendv = tendv;
        this.mota = mota;
        this.gia = gia;
    }

    // Getters và Setters
    public int getMadv() { return madv; }
    public void setMadv(int madv) { this.madv = madv; }

    public String getTendv() { return tendv; }
    public void setTendv(String tendv) { this.tendv = tendv; }

    public String getMota() { return mota; }
    public void setMota(String mota) { this.mota = mota; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
}
