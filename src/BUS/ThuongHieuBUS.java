package BUS;

import DAO.ThuongHieuDAO;
import DTO.ThuocTinhSanPham.ThuongHieuDTO;
import java.util.ArrayList;

public class ThuongHieuBUS {

    private final ThuongHieuDAO lhDAO = new ThuongHieuDAO();
    private ArrayList<ThuongHieuDTO> listLH = new ArrayList<>();

    public ThuongHieuBUS() {
        listLH = lhDAO.selectAll();
    }

    public ArrayList<ThuongHieuDTO> getAll() {
        return this.listLH;
    }

    public ThuongHieuDTO getByIndex(int index) {
        return this.listLH.get(index);
    }

    public int getIndexByMaLH(int maloaihang) {
        for (int i = 0; i < listLH.size(); i++) {
            if (listLH.get(i).getMathuonghieu() == maloaihang) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(String name) {
        int newId = lhDAO.getAutoIncrement();
        ThuongHieuDTO lh = new ThuongHieuDTO(newId, name);
        System.out.println("Thêm thương hiệu mới: ID = " + newId + ", Tên = " + name);

        boolean check = lhDAO.insert(lh) != 0;
        System.out.println("Kết quả thêm DB: " + check);

        if (check) {
            this.listLH.add(lh);
        }

        return check;
    }

    public boolean delete(ThuongHieuDTO lh) {
        System.out.println("Xóa thương hiệu ID: " + lh.getMathuonghieu());
        boolean check = lhDAO.delete(Integer.toString(lh.getMathuonghieu())) != 0;
        System.out.println("Kết quả xóa: " + check);

        if (check) {
            this.listLH.remove(lh);
        }

        return check;
    }

    public boolean update(ThuongHieuDTO lh) {
        System.out.println("Cập nhật thương hiệu ID: " + lh.getMathuonghieu() + " -> Tên: " + lh.getTenthuonghieu());
        boolean check = lhDAO.update(lh) != 0;
        System.out.println("Kết quả cập nhật: " + check);

        if (check) {
            int index = getIndexByMaLH(lh.getMathuonghieu());
            if (index != -1) {
                this.listLH.set(index, lh);
            }
        }

        return check;
    }

    public ArrayList<ThuongHieuDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<ThuongHieuDTO> result = new ArrayList<>();
        for (ThuongHieuDTO i : this.listLH) {
            if (Integer.toString(i.getMathuonghieu()).toLowerCase().contains(text)
                    || i.getTenthuonghieu().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public String[] getArrTenThuongHieu() {
        int size = listLH.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listLH.get(i).getTenthuonghieu();
        }
        return result;
    }

    public String getTenThuongHieu(int mathuonghieu) {
        int index = getIndexByMaLH(mathuonghieu);
        if (index != -1) {
            return this.listLH.get(index).getTenthuonghieu();
        }
        return null;
    }

    public boolean checkDup(String name) {
        name = name.toLowerCase();
        for (ThuongHieuDTO th : listLH) {
            if (th.getTenthuonghieu().toLowerCase().contains(name)) {
                return false;
            }
        }
        return true;
    }
}
