/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import dao.SongDAO;
import entity.Artist;
import entity.Song;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.AccentRemover;

/**
 *
 * @author tienl_000
 */
public class WebCrawler {

    public static DB db = new DB();
    private String baseUrl = "http://m.mp3.zing.vn";
    private String basePath;

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        try {
            //crawler.processPage("http://m.mp3.zing.vn");
            //crawler.processSongList("http://m.mp3.zing.vn/top-100/bai-hat-Nhac-Tre/IWZ9Z088.html");
            //crawler.processSong("http://m.mp3.zing.vn/bai-hat/Giu-Em-Di-Thuy-Chi/ZW6EZDII.html");
            //crawler.processArtist("Sơn Tùng M-TP");
            crawler.testSaveArtist();
        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processPage(String URL) throws IOException {

        Document doc = Jsoup.connect(URL).get();

        // List song by category
        Elements type = doc.select(".slist a");
        String subUrl;
        String name;
        for (int i = 0; i < type.size(); i++) {
            Element element = type.get(i);
            subUrl = element.attr("href");
            name = element.text();
            if (subUrl.contains("top")) {
                System.out.println(subUrl + "-" + name);
                try {
                    processSongList(URL + subUrl);
                } catch (SQLException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void processSongList(String URL) throws SQLException, IOException, IndexOutOfBoundsException {

        Document doc = Jsoup.connect(URL).get();

        Elements type = doc.select(".content-items");
        Elements categoryElm = doc.select(".selected[selected]");

        //TODO
        String category = categoryElm.html();
        String url;
        String title, titleSearch;
        String artist, artistSearch;
        String id;
        HashMap map;

        entity.Song songEntity;
        jaxb.song.Song songJaxb;
        SongDAO dao;

        for (int i = 0; i < type.size(); i++) {
            Element element = type.get(i);
            url = element.attr("href");
            id = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
            System.out.println("Crawl: " + url);

            title = element.select("h3").get(0).text();
            titleSearch = AccentRemover.removeAccent(title);
            artist = element.select("h4").get(0).text();
            artistSearch = AccentRemover.removeAccent(artist);

            dao = new SongDAO();

            Song found = dao.get(id);
            if (found == null) {
                try {
                    songEntity = new Song(id, title, titleSearch, artist, artistSearch, 0, category);
                    map = processSong(getBaseUrl() + url); //throw exception

                    dao.save(songEntity);
                    songJaxb = new jaxb.song.Song();

                    songJaxb.setAlbumArt(map.get("AlbumArt").toString());
                    songJaxb.setLyrics(map.get("Lyrics").toString());
                    songJaxb.setSource(map.get("Source").toString());
                    songJaxb.setTitle(title);
                    songJaxb.setArtist(artist);

                    marshallXML(getBasePath() + "song/" + id + ".xml", songJaxb);
                } catch (org.hibernate.exception.DataException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public HashMap<String, String> processSong(String URL) throws SQLException, IOException, IndexOutOfBoundsException {

        HashMap<String, String> map = new HashMap<String, String>();

        Document doc = Jsoup.connect(URL).get();

        Elements artistImg = doc.select(".artist-img");
        Elements lyricsElm = doc.select("#conLyrics");
        Elements audio = doc.select("#mp3Player");

        String albumArt = artistImg.attr("src").replace("94_94", "165_165");
        String source = audio.attr("xml");
        Document dataJson = Jsoup.connect(source).get();
        String data = dataJson.text();
        String lyrics = lyricsElm.get(0).html();

        map.put("AlbumArt", albumArt);
        map.put("Lyrics", lyrics);
        map.put("Source", data);

        return map;
    }

    public void processArtist(String name) throws SQLException, IOException {
        String url = getBaseUrl() + "/nghe-si/" + URLEncoder.encode(name, "UTF-8");
        Document doc = Jsoup.connect(url).get();

        Elements imgElm = doc.select(".artist-img");
        Elements infoElm = doc.select(".info-artist");
        Elements bioElm = doc.select("#fnBiography");

        String image = imgElm.attr("src").replace("94_94", "165_165");
        String info = infoElm.get(0).html();
        String bio = bioElm.get(0).html();

        System.out.println(image);
        System.out.println(info);
        System.out.println(bio);
    }

    public void marshallXML(String filename, jaxb.song.Song song) {
        try {
            JAXBContext context = JAXBContext.newInstance(jaxb.song.Song.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(song, new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void marshallXML(String filename, Artist artist) {
        try {
            JAXBContext context = JAXBContext.newInstance(Artist.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(artist, new File(filename));
        } catch (Exception e) {
        }
    }

    public void testSaveArtist() throws SQLException, IOException {
        String category = "Nhạc trẻ";
        String url = "blah";
        String title = "Nhạc của tui";
        String artist = "Lê Tiến";
        String id = "WB024525";


        entity.Song songEntity;
        jaxb.song.Song songJaxb;
        SongDAO dao = new SongDAO();

        Song found = dao.get(id);
        if (found == null) {
            //songEntity = new Song(id, title, artist, 0, 0, category);
            //dao.save(songEntity);

            songJaxb = new jaxb.song.Song();

            songJaxb.setAlbumArt("AlbumArt");
            songJaxb.setLyrics("Lyrics");
            songJaxb.setSource("Source");
            songJaxb.setTitle(title);
            songJaxb.setArtist(artist);

            //marshallXML(getBasePath() + "song/" + id + ".xml", songJaxb);

            marshallXML("web/song/" + id + ".xml", songJaxb);
        }

    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @param basePath the basePath to set
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
