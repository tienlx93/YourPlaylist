/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author tienl_000
 */
public class AccountDAO {
    private Session session;

    public AccountDAO() {
        SessionFactory sf = util.HibernateUtil.getSessionFactory();
        session = sf.getCurrentSession();
    }

    public Account login(String email, String password) {
        try {
            session.getTransaction().begin();
            String sql = "from Account where email = ? and password = ?";
            Query query = session.createQuery(sql);
            query.setString(0, email);
            query.setString(1, password);
            entity.Account account = (Account) query.uniqueResult();
            session.flush();
            session.getTransaction().commit();
            if (account != null) {
                return account;
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean register(Account account) {
        try {
            session.getTransaction().begin();
            session.save(account);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int findAccountId(String username) {

        try {
            session.getTransaction().begin();
            String sql = "from AccountAccount where username = ?";
            Query query = session.createQuery(sql);
            query.setString(0, username);
            Account account = (Account) query.uniqueResult();
            session.flush();
            session.getTransaction().commit();
            if (account != null) {
                return account.getId();
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public boolean update(Account newAccount, int userId) {
        try {
            session.getTransaction().begin();
            Account account = (Account) session.get(Account.class, userId);
            account.setPassword(newAccount.getPassword());
            account.setEmail(newAccount.getEmail());
            session.update(account);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
