package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {

    private final KhachHangDAO khDAO = new KhachHangDAO();
    public ArrayList<KhachHangDTO> listKhachHang;

    public KhachHangBUS() {
        listKhachHang = khDAO.selectAll();
    }

    public ArrayList<KhachHangDTO> getAll() {
        return listKhachHang;
    }

    public KhachHangDTO getByIndex(int index) {
        if (index >= 0 && index < listKhachHang.size()) {
            return listKhachHang.get(index);
        }
        return null;
    }

    public int getIndexByMaDV(int makhachhang) {
        for (int i = 0; i < listKhachHang.size(); i++) {
            if (listKhachHang.get(i).getMaKH() == makhachhang) {
                return i;
            }
        }
        return -1;
    }

    public Boolean add(KhachHangDTO kh) {
        boolean check = khDAO.insert(kh) != 0;
        if (check) {
            listKhachHang.add(kh);
        }
        return check;
    }

    public Boolean delete(KhachHangDTO kh) {
        int index = getIndexByMaDV(kh.getMaKH());
        if (index == -1) return false;

        boolean check = khDAO.delete(String.valueOf(kh.getMaKH())) != 0;
        if (check) {
            listKhachHang.remove(index);
        }
        return check;
    }

    public Boolean update(KhachHangDTO kh) {
        int index = getIndexByMaDV(kh.getMaKH());
        if (index == -1) return false;

        boolean check = khDAO.update(kh) != 0;
        if (check) {
            listKhachHang.set(index, kh);
        }
        return check;
    }

    public ArrayList<KhachHangDTO> search(String text, String type) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        String keyword = text.toLowerCase();

        for (KhachHangDTO i : listKhachHang) {
            switch (type) {
                case "Tất cả" -> {
                    if (String.valueOf(i.getMaKH()).contains(keyword) ||
                        i.getHoten().toLowerCase().contains(keyword) ||
                        i.getDiachi().toLowerCase().contains(keyword) ||
                        i.getSdt().toLowerCase().contains(keyword)) {
                        result.add(i);
                    }
                }
                case "Mã khách hàng" -> {
                    if (String.valueOf(i.getMaKH()).contains(keyword)) {
                        result.add(i);
                    }
                }
                case "Tên khách hàng" -> {
                    if (i.getHoten().toLowerCase().contains(keyword)) {
                        result.add(i);
                    }
                }
                case "Địa chỉ" -> {
                    if (i.getDiachi().toLowerCase().contains(keyword)) {
                        result.add(i);
                    }
                }
                case "Số điện thoại" -> {
                    if (i.getSdt().toLowerCase().contains(keyword)) {
                        result.add(i);
                    }
                }
            }
        }

        return result;
    }

    public String getTenKhachHang(int makh) {
        for (KhachHangDTO kh : listKhachHang) {
            if (kh.getMaKH() == makh) {
                return kh.getHoten();
            }
        }
        return "";
    }

    public String[] getArrTenKhachHang() {
        String[] result = new String[listKhachHang.size()];
        for (int i = 0; i < listKhachHang.size(); i++) {
            result[i] = listKhachHang.get(i).getHoten();
        }
        return result;
    }

    public KhachHangDTO selectKh(int makh) {
        return khDAO.selectById(String.valueOf(makh));
    }
}
