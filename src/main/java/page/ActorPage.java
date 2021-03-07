package page;

// Import necessary packages
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Class for managing the Actors main page. Retrieve certain information from collection
 * in order to load dynamic UI pages for users.
 * @author Jason C. Nucciarone
 *
 */
public class ActorPage extends Page {
    private final String DatabaseName = "ACCOUNTS";
    private final String CollectionName = "ACTOR_PAGES";

    public ActorPage(){
        super();
    }

    public ActorPage(String id){
        super(id);
    }

    @Override
    public void createPage(Document doc){
        // Set page id in Document
        doc.append("page_id", getPageid());

        // Pull in Actors Document and then add settings portion to it
        doc.append("history", "");

        // Add new document to ACTOR_PAGES collection
        try {
            // Connect to database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Add the documents to the ACTOR_PAGES collection
            table.insertOne(doc);

        } catch (Exception e){
            String error = "Failed to create a page for the actor! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }
    }

    @Override
    public void updatePage(Document doc){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Update the document
            table.updateOne(Filters.eq("page_id", getPageid()), doc);

        } catch (Exception e){
            String error = "Failed to update the page for the actor! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }
    }

    @Override
    public void removePage(Document doc){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Delete document from the collection
            table.deleteOne(Filters.eq("page_id", getPageid()));

        } catch (Exception e){
            String error = "Failed to delete the page for the actor! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }
    }

    public Document renderPage(String id){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Grab the page
            BasicDBObject query = new BasicDBObject("page_id", getPageid());
            try (MongoCursor<Document> cursor = table.find(query).iterator()){
                if (cursor.hasNext()){
                    setDocument(cursor.next());
                }
            }

        } catch (Exception e){
            String error = "Failed to render page! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }

        return getDocument();
    }

    public void updatePreferences(ArrayList<String> preferences){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Update the preferences for the page in the document
            table.updateOne(Filters.eq("page_id", getPageid()), Updates.set("preferences",
                    preferences.toArray()));

        } catch (Exception e){
            String error = "Failed to update Actor preferences! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }
    }

    public Document getHistory(){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Grab history from the page document
            BasicDBObject query = new BasicDBObject("page_id", getPageid());
            try (MongoCursor<Document> cursor = table.find(query).iterator()){
                if (cursor.hasNext()){
                    setDocument(cursor.next());
                }
            }

        } catch (Exception e){
            String error = "Failed to get Actor history! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }

        // After getting specific Document, pull out history
        return new Document("history", getDocument().get("history"));
    }

    public void updateSettings(ArrayList<String> settings){
        try {
            // Connect to the database
            MongoClient mongoClient = getConn();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseName);

            // Get collection
            MongoCollection<Document> table = mongoDatabase.getCollection(CollectionName);

            // Update the settings for the page in the document
            table.updateOne(Filters.eq("page_id", getPageid()), Updates.set("settings",
                    settings.toArray()));

        } catch (Exception e){
            String error = "Failed to update Actor settings! Check MongoDB instance.";
            System.out.println(error);
            e.printStackTrace();
        }
    }
}
