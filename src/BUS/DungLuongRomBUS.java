package BUS;

import DAO.DungLuongRomDAO;
import DTO.ThuocTinhSanPham.DungLuongRomDTO;
import java.util.ArrayList;

public class DungLuongRomBUS {

    private final DungLuongRomDAO dlromDAO = new DungLuongRomDAO();
    private ArrayList<DungLuongRomDTO> listDLRom;

    public DungLuongRomBUS() {
        this.listDLRom = dlromDAO.selectAll();
    }

    public ArrayList<DungLuongRomDTO> getAll() {
        return this.listDLRom;
    }

    public DungLuongRomDTO getByIndex(int index) {
        if (index >= 0 && index < listDLRom.size()) {
            return listDLRom.get(index);
        }
        return null;
    }

    public int getIndexByMaRom(int marom) {
        for (int i = 0; i < listDLRom.size(); i++) {
            if (listDLRom.get(i).getMadungluongrom() == marom) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(DungLuongRomDTO dlrom) {
        boolean success = dlromDAO.insert(dlrom) != 0;
        if (success) {
            listDLRom.add(dlrom);
        }
        return success;
    }

    public boolean delete(DungLuongRomDTO dlrom, int index) {
        if (index >= 0 && index < listDLRom.size()) {
            boolean success = dlromDAO.delete(String.valueOf(dlrom.getMadungluongrom())) != 0;
            if (success) {
                listDLRom.remove(index);
            }
            return success;
        }
        return false;
    }

    public boolean update(DungLuongRomDTO dlrom) {
        int index = getIndexById(dlrom.getMadungluongrom());
        if (index != -1) {
            boolean success = dlromDAO.update(dlrom) != 0;
            if (success) {
                listDLRom.set(index, dlrom);
            }
            return success;
        }
        return false;
    }

    public int getIndexById(int madlrom) {
        for (int i = 0; i < listDLRom.size(); i++) {
            if (listDLRom.get(i).getMadungluongrom() == madlrom) {
                return i;
            }
        }
        return -1;
    }

    public int getKichThuocById(int madlrom) {
        int index = getIndexById(madlrom);
        if (index != -1) {
            return listDLRom.get(index).getDungluongrom();
        }
        return -1; // hoặc throw exception tùy cách xử lý
    }

    public String[] getArrKichThuoc() {
        String[] result = new String[listDLRom.size()];
        for (int i = 0; i < listDLRom.size(); i++) {
            result[i] = listDLRom.get(i).getDungluongrom() + "GB";
        }
        return result;
    }

    public boolean checkDup(int dl) {
        for (DungLuongRomDTO dto : listDLRom) {
            if (dto.getDungluongrom() == dl) {
                return false;
            }
        }
        return true;
    }
}
