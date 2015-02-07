/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Artist;

/**
 *
 * @author tienl_000
 */
public class ArtistDAO extends BaseDAO<Artist, String> {

    public ArtistDAO() {
        super(Artist.class);
    }

}
