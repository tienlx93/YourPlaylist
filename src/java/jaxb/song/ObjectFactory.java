//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.03 at 09:42:56 PM ICT 
//


package jaxb.song;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jaxb.song package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Song_QNAME = new QName("http://yourplaylist.tk/Schema", "Song");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jaxb.song
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Song }
     * 
     */
    public Song createSong() {
        return new Song();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Song }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://yourplaylist.tk/Schema", name = "Song")
    public JAXBElement<Song> createSong(Song value) {
        return new JAXBElement<Song>(_Song_QNAME, Song.class, null, value);
    }

}
