/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.dialect.SQLServerDialect;

/**
 *
 * @author tienl_000
 */
public class SQLServerNativeDialect extends SQLServerDialect {
     public SQLServerNativeDialect() {
         super();
         registerColumnType(Types.VARCHAR, "nvarchar($l)");
         registerColumnType(Types.CLOB, "nvarchar(max)");
     }

    @Override
    public String getTypeName(int code, int length, int precision, int scale) throws HibernateException {
        if(code != 2005) {
            return super.getTypeName(code, length, precision, scale);
        } else {
            return "ntext";
        }
    }
}
