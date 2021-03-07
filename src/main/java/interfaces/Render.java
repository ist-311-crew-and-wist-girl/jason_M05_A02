package interfaces;

import org.bson.Document;

/**
 * Interface implemented by classes that need to return a renderable Document.
 * @author Jason C. Nucciarone
 *
 */
public interface Render {
    public Document renderPage(String id);
}
