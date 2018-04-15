
import java.io.IOException;

import javax.annotation.Nonnull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpRequests {

    public static enum METHOD {
	GET, POST, PUT, DELETE;
    }

    /**
     * Executes http request and returns {@link Document} containing the http
     * response body.
     * 
     * @param endpoint
     *            url endpoint to send http request to. not null. not empty when
     *            trimmed.
     * @param requestMethod
     *            http request method to be used. note: this method only supports
     *            the following {@link METHOD} types: GET, POST. not null.
     * @return {@link Document} containing the http response body
     * @throws IOException
     *             if unable to connect to endpoint
     * @throws IllegalArgumentException
     *             if illegal null or empty arguments are detected
     * @throws UnsupportedOperationException
     *             if an illegal {@link METHOD} argument is provided
     */
    public static Document send(@Nonnull String endpoint, @Nonnull METHOD requestMethod) throws IOException {
	if (endpoint == null || requestMethod == null)
	    throw new IllegalArgumentException("Unable to retrieve http response document: illegal null argument(s).");
	endpoint = endpoint.trim();
	if (endpoint.isEmpty())
	    throw new IllegalArgumentException(
		    "Unable to retrieve http response document: illegal empty string argument(s).");
	Document doc = null;
	long startTime = System.currentTimeMillis();
	switch (requestMethod) {
	case GET:
	    doc = Jsoup.connect(endpoint).get();
	    break;
	case POST:
	    doc = Jsoup.connect(endpoint).post();
	    break;
	default:
	    throw new UnsupportedOperationException(
		    "Unable to retrieve http response document: request method unsupported.");
	}
	long stopTime = System.currentTimeMillis();
	System.out.println("HTTP CONNECTION (" + (stopTime - startTime) + " ms): " + endpoint);
	return doc;
    }

}
