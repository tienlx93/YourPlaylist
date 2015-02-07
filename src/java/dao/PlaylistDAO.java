/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Playlist;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author tienl_000
 */
public class PlaylistDAO extends BaseDAO<Playlist, String> {

    public PlaylistDAO() {
        super(Playlist.class);
    }

    public String getNewId() {
        String id;
        try {
            session.getTransaction().begin();
            String sql = "select MAX(CAST(Id AS int)) FROM Playlist";
            Query query = session.createSQLQuery(sql);
            List rows = query.list();
            if (rows.size() > 0 && rows.get(0) != null) {
                String number = rows.get(0).toString();
                int value = Integer.parseInt(number);
                id = (value + 1) + "";
            } else {
                id = "1";
            }

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }

        return id;
    }
    
    public String getLastIdByEmail(String email) {
        String lastId = null;
        try {
            session.getTransaction().begin();
            String sql = "select MAX(CAST(Id AS int)) FROM Playlist WHERE accountEmail LIKE ?";
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, email);
            Object result = query.uniqueResult();
            if (result != null) {
                lastId = result.toString();        
            }
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return lastId;
    }
    
    public List<Playlist> getPlaylistByEmail(String email) {
        List<Playlist> playlists = null;
        try {
            session.getTransaction().begin();
            String sql = "FROM Playlist WHERE accountEmail LIKE ?";
            Query query = session.createQuery(sql);
            query.setParameter(0, email);
            playlists = query.list();    

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return playlists;
    }
    
    public List<Playlist> getTopPlaylist(int limit) {
        List<Playlist> playlists = null;
        try {
            session.getTransaction().begin();
            String sql = "FROM Playlist ORDER BY PlayCount DESC";
            Query query = session.createQuery(sql);
            playlists = query.setMaxResults(limit).list();    

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return playlists;
    }
}