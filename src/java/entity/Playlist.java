package entity;
// Generated 06/02/2015 11:56:43 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Playlist generated by hbm2java
 */
@Entity
@Table(name="Playlist"
    ,schema="dbo"
    ,catalog="YourPlaylist"
)
public class Playlist  implements java.io.Serializable {


     private String id;
     private String name;
     private long playCount;
     private String accountEmail;
     private String accountName;

    public Playlist() {
    }

    public Playlist(String id, String name, long playCount, String accountEmail, String accountName) {
       this.id = id;
       this.name = name;
       this.playCount = playCount;
       this.accountEmail = accountEmail;
       this.accountName = accountName;
    }
   
     @Id 
    
    @Column(name="Id", unique=true, nullable=false, length=10)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="Name", nullable=false, length=150)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="PlayCount", nullable=false)
    public long getPlayCount() {
        return this.playCount;
    }
    
    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }
    
    @Column(name="AccountEmail", nullable=false, length=50)
    public String getAccountEmail() {
        return this.accountEmail;
    }
    
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }
    
    @Column(name="AccountName", nullable=false, length=50)
    public String getAccountName() {
        return this.accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }




}


