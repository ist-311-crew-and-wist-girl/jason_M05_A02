package page;

// Import necessary modules
import java.sql.Timestamp;
import java.util.Calendar;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.types.ObjectId;

import utils.ConnectDB;
import interfaces.Render;

/**
 * Abstract parent class for Pages that need to be loaded by the application.
 * Initialize methods for interacting with created pages.
 * @author Jason C. Nucciarone
 *
 */
public abstract class Page implements Render {
    private String timestamp;
    private String pageid;
    private Document document;
    private MongoClient conn;

    // Default constructor
    public Page(){
        // Initialize variables needed by all pages
        Calendar calendar = Calendar.getInstance();
        Timestamp temp_timestamp = new Timestamp(calendar.getTime().getTime());
        this.timestamp = temp_timestamp.toString();

        this.pageid = new ObjectId().toString();

        this.document = new Document();

        this.conn = ConnectDB.getConnection();
    }

    // Constructor for if page already exists
    public Page(String id){
        this.pageid = id;
    }

    public abstract void createPage(Document doc);

    public abstract void updatePage(Document doc);

    public abstract void removePage(Document doc);

    // Setters and getters to be used by sub-classes
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public MongoClient getConn() {
        return conn;
    }

    public void setConn(MongoClient conn) {
        this.conn = conn;
    }
}
