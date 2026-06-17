package services;

import dao.ChuDeDAO;
import models.ChuDe;
import java.util.List;

public class ChuDeService {
    private final ChuDeDAO chuDeDAO = new ChuDeDAO();

    public List<ChuDe> getAllTopics() {
        return chuDeDAO.selectAll();
    }

    public ChuDe getTopicById(int id) {
        return chuDeDAO.selectById(id);
    }

    public ChuDe getTopicByName(String name) {
        return chuDeDAO.selectByName(name);
    }

    public boolean addTopic(String name) {
        if (getTopicByName(name) != null) {
            return false;
        }
        ChuDe cd = new ChuDe(0, name);
        return chuDeDAO.insert(cd);
    }

    public boolean updateTopic(ChuDe cd) {
        return chuDeDAO.update(cd);
    }

    public boolean deleteTopic(int id) {
        return chuDeDAO.delete(id);
    }

    public int countTotal() {
        return chuDeDAO.countTotal();
    }
}
