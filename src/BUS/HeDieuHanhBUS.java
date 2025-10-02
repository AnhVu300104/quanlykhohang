package BUS;

import DAO.HeDieuHanhDAO;
import DTO.ThuocTinhSanPham.HeDieuHanhDTO;
import java.util.ArrayList;

/**
 *
 * @author 84907
 */
public class HeDieuHanhBUS {

    private HeDieuHanhDAO hdhDAO = new HeDieuHanhDAO();
    private ArrayList<HeDieuHanhDTO> listHeDieuHanh = new ArrayList<>();

    public HeDieuHanhBUS() {
        this.listHeDieuHanh = hdhDAO.selectAll();
    }

    // Trả về danh sách hệ điều hành
    public ArrayList<HeDieuHanhDTO> getAll() {
        return this.listHeDieuHanh;
    }

    // Trả về mảng tên hệ điều hành để hiển thị
    public String[] getArrTenHeDieuHanh() {
        String[] result = new String[listHeDieuHanh.size()];
        for (int i = 0; i < listHeDieuHanh.size(); i++) {
            result[i] = listHeDieuHanh.get(i).getTenhdh();
        }
        return result;
    }

    // Lấy DTO theo vị trí trong danh sách
    public HeDieuHanhDTO getByIndex(int index) {
        return this.listHeDieuHanh.get(index);
    }

    // Thêm mới hệ điều hành
    public boolean add(HeDieuHanhDTO hdh) {
        boolean check = hdhDAO.insert(hdh) != 0;
        if (check) {
            this.listHeDieuHanh.add(hdh);
        }
        return check;
    }

    // Xóa hệ điều hành khỏi danh sách
    public boolean delete(HeDieuHanhDTO hdh, int index) {
        boolean check = hdhDAO.delete(Integer.toString(hdh.getMahdh())) != 0;
        if (check) {
            this.listHeDieuHanh.remove(index);
        }
        return check;
    }

    // Tìm vị trí theo mã hệ điều hành
    public int getIndexByMaMau(int mamau) {
        int i = 0;
        int vitri = -1;
        while (i < this.listHeDieuHanh.size() && vitri == -1) {
            if (listHeDieuHanh.get(i).getMahdh() == mamau) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    // Trả về tên hệ điều hành theo mã
    public String getTenHdh(int mahdh) {
        int index = this.getIndexByMaMau(mahdh);
        return (index != -1) ? this.listHeDieuHanh.get(index).getTenhdh() : null;
    }

    // Cập nhật hệ điều hành
    public boolean update(HeDieuHanhDTO hdh) {
        boolean check = hdhDAO.update(hdh) != 0;
        if (check) {
            this.listHeDieuHanh.set(getIndexByMaMau(hdh.getMahdh()), hdh);
        }
        return check;
    }

    // Kiểm tra trùng tên hệ điều hành (không phân biệt hoa thường, loại bỏ khoảng trắng đầu cuối)
    public boolean checkDup(String name) {
        name = name.trim().toLowerCase();
        for (HeDieuHanhDTO hdh : listHeDieuHanh) {
            if (hdh.getTenhdh().trim().toLowerCase().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
