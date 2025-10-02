package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

/**
 *
 * @author Dao Anh Vu
 */
public class NhaCungCapBUS {

    private final NhaCungCapDAO NccDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCapDTO> listNcc = new ArrayList<>();

    public NhaCungCapBUS() {
        this.listNcc = NccDAO.selectAll();
    }

    public ArrayList<NhaCungCapDTO> getAll() {
        return this.listNcc;
    }

    public NhaCungCapDTO getByIndex(int index) {
        if (index >= 0 && index < listNcc.size()) {
            return listNcc.get(index);
        }
        return null;
    }

    public boolean add(NhaCungCapDTO ncc) {
        boolean check = NccDAO.insert(ncc) != 0;
        if (check) {
            listNcc.add(ncc);
        }
        return check;
    }

    public boolean delete(NhaCungCapDTO ncc, int index) {
        boolean check = NccDAO.delete(Integer.toString(ncc.getMancc())) != 0;
        if (check && index >= 0 && index < listNcc.size()) {
            listNcc.remove(index);
        }
        return check;
    }

    public boolean update(NhaCungCapDTO ncc) {
        boolean check = NccDAO.update(ncc) != 0;
        if (check) {
            int idx = getIndexByMaNCC(ncc.getMancc());
            if (idx != -1) {
                listNcc.set(idx, ncc);
            }
        }
        return check;
    }

    public int getIndexByMaNCC(int mancc) {
        for (int i = 0; i < listNcc.size(); i++) {
            if (listNcc.get(i).getMancc() == mancc) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<NhaCungCapDTO> search(String txt, String type) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();

        for (NhaCungCapDTO i : listNcc) {
            String ma = String.valueOf(i.getMancc());
            String ten = i.getTenncc().toLowerCase();
            String diachi = i.getDiachi().toLowerCase();
            String email = i.getEmail().toLowerCase();
            String sdt = i.getSdt().toLowerCase();

            switch (type) {
                case "Tất cả" -> {
                    if (ma.contains(txt) || ten.contains(txt) || diachi.contains(txt) || email.contains(txt) || sdt.contains(txt)) {
                        result.add(i);
                    }
                }
                case "Mã nhà cung cấp" -> {
                    if (ma.contains(txt)) result.add(i);
                }
                case "Tên nhà cung cấp" -> {
                    if (ten.contains(txt)) result.add(i);
                }
                case "Địa chỉ" -> {
                    if (diachi.contains(txt)) result.add(i);
                }
                case "Số điện thoại" -> {
                    if (sdt.contains(txt)) result.add(i);
                }
                case "Email" -> {
                    if (email.contains(txt)) result.add(i);
                }
            }
        }

        return result;
    }

    public String[] getArrTenNhaCungCap() {
        String[] result = new String[listNcc.size()];
        for (int i = 0; i < listNcc.size(); i++) {
            result[i] = listNcc.get(i).getTenncc();
        }
        return result;
    }

    public String getTenNhaCungCap(int mancc) {
        int index = getIndexByMaNCC(mancc);
        return index != -1 ? listNcc.get(index).getTenncc() : "";
    }

    public NhaCungCapDTO findCT(ArrayList<NhaCungCapDTO> ncc, String tenncc) {
        for (NhaCungCapDTO item : ncc) {
            if (item.getTenncc().equals(tenncc)) {
                return item;
            }
        }
        return null;
    }
}
