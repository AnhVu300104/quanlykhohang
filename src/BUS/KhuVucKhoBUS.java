package BUS;

import DAO.KhuVucKhoDAO;
import DTO.KhuVucKhoDTO;
import java.util.ArrayList;

public class KhuVucKhoBUS {

    private final KhuVucKhoDAO kvkDAO = new KhuVucKhoDAO();
    private ArrayList<KhuVucKhoDTO> listKVK;

    public KhuVucKhoBUS() {
        listKVK = kvkDAO.selectAll();
    }

    public KhuVucKhoBUS getInstance() {
        return new KhuVucKhoBUS();
    }

    public ArrayList<KhuVucKhoDTO> getAll() {
        return listKVK;
    }

    public KhuVucKhoDTO getByIndex(int index) {
        if (index >= 0 && index < listKVK.size()) {
            return listKVK.get(index);
        }
        return null;
    }

    public int getIndexByMaLH(int makhuvuc) {
        for (int i = 0; i < listKVK.size(); i++) {
            if (listKVK.get(i).getMakhuvuc() == makhuvuc) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(KhuVucKhoDTO kvk) {
        boolean check = kvkDAO.insert(kvk) != 0;
        if (check) {
            listKVK.add(kvk);
        }
        return check;
    }

    public boolean delete(KhuVucKhoDTO kvk, int index) {
        if (index < 0 || index >= listKVK.size()) return false;
        boolean check = kvkDAO.delete(Integer.toString(kvk.getMakhuvuc())) != 0;
        if (check) {
            listKVK.remove(index);
        }
        return check;
    }

    public boolean update(KhuVucKhoDTO kvk) {
        boolean check = kvkDAO.update(kvk) != 0;
        if (check) {
            int index = getIndexByMaKVK(kvk.getMakhuvuc());
            if (index != -1) {
                listKVK.set(index, kvk);
            }
        }
        return check;
    }

    public int getIndexByMaKVK(int makvk) {
        for (int i = 0; i < listKVK.size(); i++) {
            if (listKVK.get(i).getMakhuvuc() == makvk) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<KhuVucKhoDTO> search(String txt, String type) {
        ArrayList<KhuVucKhoDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();

        for (KhuVucKhoDTO i : listKVK) {
            String ma = Integer.toString(i.getMakhuvuc());
            String ten = i.getTenkhuvuc().toLowerCase();

            switch (type) {
                case "Tất cả" -> {
                    if (ma.contains(txt) || ten.contains(txt)) result.add(i);
                }
                case "Mã khu vực kho" -> {
                    if (ma.contains(txt)) result.add(i);
                }
                case "Tên khu vực kho" -> {
                    if (ten.contains(txt)) result.add(i);
                }
            }
        }
        return result;
    }

    public String[] getArrTenKhuVuc() {
        String[] result = new String[listKVK.size()];
        for (int i = 0; i < listKVK.size(); i++) {
            result[i] = listKVK.get(i).getTenkhuvuc();
        }
        return result;
    }

    public String getTenKhuVuc(int makhuvuc) {
        int index = getIndexByMaKVK(makhuvuc);
        if (index != -1) {
            return listKVK.get(index).getTenkhuvuc();
        }
        return null;
    }
}
