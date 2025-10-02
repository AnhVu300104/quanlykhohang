package BUS;

import DAO.DungLuongRamDAO;
import DTO.ThuocTinhSanPham.DungLuongRamDTO;
import java.util.ArrayList;

public class DungLuongRamBUS {
    private final DungLuongRamDAO dlramDAO = new DungLuongRamDAO();
    private ArrayList<DungLuongRamDTO> listDLRam = new ArrayList<>();

    public DungLuongRamBUS() {
        listDLRam = dlramDAO.selectAll();
    }

    public static DungLuongRamBUS getInstance() {
        return new DungLuongRamBUS();
    }

    public ArrayList<DungLuongRamDTO> getAll() {
        return this.listDLRam;
    }

    public DungLuongRamDTO getByIndex(int index) {
        if (index >= 0 && index < listDLRam.size()) {
            return this.listDLRam.get(index);
        }
        return null;
    }

    public int getIndexByMaRam(int maram) {
        for (int i = 0; i < listDLRam.size(); i++) {
            if (listDLRam.get(i).getMadlram() == maram) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(DungLuongRamDTO dlram) {
        boolean check = dlramDAO.insert(dlram) != 0;
        if (check) {
            this.listDLRam.add(dlram);
        }
        return check;
    }

    public boolean delete(DungLuongRamDTO dlram, int index) {
        boolean check = dlramDAO.delete(Integer.toString(dlram.getMadlram())) != 0;
        if (check && index >= 0 && index < listDLRam.size()) {
            this.listDLRam.remove(index);
        }
        return check;
    }

    public boolean update(DungLuongRamDTO dlram) {
        boolean check = dlramDAO.update(dlram) != 0;
        int index = getIndexById(dlram.getMadlram());
        if (check && index != -1) {
            this.listDLRam.set(index, dlram);
        }
        return check;
    }

    public int getIndexById(int madlram) {
        for (int i = 0; i < listDLRam.size(); i++) {
            if (listDLRam.get(i).getMadlram() == madlram) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkDup(int dl) {
        for (DungLuongRamDTO item : listDLRam) {
            if (item.getDungluongram() == dl) {
                return false;
            }
        }
        return true;
    }

    public int getKichThuocById(int madlram) {
        int index = getIndexById(madlram);
        if (index != -1) {
            return listDLRam.get(index).getDungluongram();
        }
        return -1;
    }

    public String[] getArrKichThuoc() {
        String[] result = new String[listDLRam.size()];
        for (int i = 0; i < listDLRam.size(); i++) {
            result[i] = listDLRam.get(i).getDungluongram() + "GB";
        }
        return result;
    }
}
