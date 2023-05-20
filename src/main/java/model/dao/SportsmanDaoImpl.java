package model.dao;

import model.entity.Sportsman;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class SportsmanDaoImpl implements Dao<Sportsman> {

    @Override
    public Sportsman getById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        return session.get(Sportsman.class, id);
    }

    @Override
    public void update(Sportsman entity) {

    }

    @Override
    public void delete(Sportsman entity) {

    }

    @Override
    public List<Sportsman> getALl() {
        return null;
    }

    public void add(Sportsman sportsman) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(sportsman);
        tx1.commit();
        session.close();
    }
}
