package services;

import dao.KetQuaKiemTraDAO;
import models.KetQuaKiemTra;
import java.util.List;

public class KetQuaKiemTraService {
    private final KetQuaKiemTraDAO ketQuaKiemTraDAO = new KetQuaKiemTraDAO();

    public List<KetQuaKiemTra> getAllResults() {
        return ketQuaKiemTraDAO.selectAll();
    }

    public List<KetQuaKiemTra> getResultsByAccount(int accountId) {
        return ketQuaKiemTraDAO.selectByTaiKhoan(accountId);
    }

    public List<Object[]> getDetailsByTopic(int topicId) {
        return ketQuaKiemTraDAO.selectDetailsByChuDe(topicId);
    }

    public boolean saveResult(KetQuaKiemTra kq) {
        return ketQuaKiemTraDAO.insert(kq);
    }

    public int countTopicsCompleted(int accountId) {
        return ketQuaKiemTraDAO.countChuDeDaLam(accountId);
    }

    public double getOverallAverageScore(int accountId) {
        return ketQuaKiemTraDAO.getDiemTrungBinh(accountId);
    }

    public double getMaxScore(int accountId, int topicId) {
        return ketQuaKiemTraDAO.getMaxDiemChuDe(accountId, topicId);
    }

    public int getAttemptCount(int accountId, int topicId) {
        return ketQuaKiemTraDAO.getSoLanLamChuDe(accountId, topicId);
    }

    public double getAverageScore(int accountId, int topicId) {
        return ketQuaKiemTraDAO.getDiemTrungBinhChuDe(accountId, topicId);
    }
}
