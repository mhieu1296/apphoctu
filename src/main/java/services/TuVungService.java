package services;

import dao.TuVungDAO;
import models.TuVung;
import java.util.List;

public class TuVungService {
    private final TuVungDAO tuVungDAO = new TuVungDAO();

    public List<TuVung> getAllVocabulary() {
        return tuVungDAO.selectAll();
    }

    public List<TuVung> getVocabularyByTopic(int topicId) {
        return tuVungDAO.selectByChuDe(topicId);
    }

    public boolean addVocabulary(TuVung tv) {
        return tuVungDAO.insert(tv);
    }

    public boolean updateVocabulary(TuVung tv) {
        return tuVungDAO.update(tv);
    }

    public boolean deleteVocabulary(int id) {
        return tuVungDAO.delete(id);
    }

    public int countTotal() {
        return tuVungDAO.countTotal();
    }

    public int countByTopic(int topicId) {
        return tuVungDAO.countByChuDe(topicId);
    }
}
