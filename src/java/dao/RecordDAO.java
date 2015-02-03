/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Record;

/**
 *
 * @author tienl_000
 */
public class RecordDAO extends BaseDAO<Record, Integer> {

    public RecordDAO() {
        super(Record.class);
    }
    
}
