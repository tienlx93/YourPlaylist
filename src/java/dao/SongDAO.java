/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Song;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author tienl_000
 */
public class SongDAO extends BaseDAO<Song, String> {

    public SongDAO() {
        super(Song.class);
    }

    
}
