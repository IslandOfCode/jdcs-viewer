package it.islandofcode.jdcsviewer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;

import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.ds.ipcam.IpCamAuth;

/**
 * The code for this class is heavly based (read: copied) from the IpCamDevice.java class
 * by Bartosz Firyn (sarxos)
 * @author Pier Riccardo Monzo
 */
public class HttpUtils {

	private HttpClient client;
	private HttpContext context;
	private IpCamAuth auth;
	private URL url;

	public HttpUtils(String url, IpCamAuth auth) {
		this.auth = auth;
		this.url = toURL(url); //va prima di createContext, perchè ne fa uso!
		this.client = createClient();
		this.context = createContext();
	}

	private HttpClient createClient() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder.setConnectTimeout(1000);
		requestBuilder.setConnectionRequestTimeout(1000);

		return HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).build();
	}

	private HttpContext createContext() {

		if (auth == null) {
			return null;
		}

		final URI uri = toURI(url);
		final HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		final Credentials credentials = new UsernamePasswordCredentials(auth.getUserName(), auth.getPassword());
		final CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, credentials);

		final AuthCache cache = new BasicAuthCache();
		cache.put(host, new BasicScheme());

		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(provider);
		context.setAuthCache(cache);

		return context;
	}
	
	public InputStream get(final URI uri) throws UnsupportedOperationException, IOException {

		final HttpGet get = new HttpGet(uri);
		final HttpResponse response = client.execute(get, context);
		final HttpEntity entity = response.getEntity();
		
		String status = String.valueOf(response.getStatusLine().getStatusCode());
		
		if(!status.matches("20[0-9]{1}"))
			throw new IOException(uri.toString() +" RETURNED " + response.getStatusLine().getStatusCode());
		
		
		return (entity!=null)?entity.getContent():null;
	}
	
	public static final URL toURL(String url) {
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new WebcamException(String.format("Incorrect URL '%s'", url), e);
		}
	}

	public static final URI toURI(URL url) {
		try {
			return url.toURI();
		} catch (URISyntaxException e) {
			throw new WebcamException(e);
		}
	}
	
	/**
	 * This method will send HTTP HEAD request to the camera URL to check whether it's online or
	 * offline. It's online when this request succeed and it's offline if any exception occurs or
	 * response code is 404 Not Found.
	 *
	 * @return True if camera is online, false otherwise
	 */
	public boolean isOnline() {
		try {
			return client
				.execute(new HttpHead(toURI(this.url)))
				.getStatusLine()
				.getStatusCode() != 404;
		} catch (Exception e) {
			return false;
		}
	}
}
