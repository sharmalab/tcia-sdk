package edu.emory.cci.tcia.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import edu.emory.cci.tcia.client.core.OutputFormat;
import edu.emory.cci.tcia.client.exceptions.TCIAClientException;
import edu.emory.cci.tcia.client.conf.TCIAConf;
import edu.emory.cci.tcia.client.conf.TCIAConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import org.apache.http.client.HttpClient;


/**
 * The utility methods of the TCIA client.
 */
public class TCIAClientUtil {

	private static String authValue;
	private static String AUTHORIZATION_HEADER;
	private static String RESOURCE_URL;
	private static HttpClient httpClient;

	private static Logger logger = LogManager.getLogger(TCIAClientUtil.class.getName());


	/**
	 * Initialize the configuration parameters based on the values presented in the configuration file.
	 */
	public static void init() {
		httpClient = new DefaultHttpClient();
		httpClient = WebClientDevWrapper.wrapClient(httpClient);

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			TCIAConf tciaConf = mapper.readValue(new File(TCIAConstants.TCIA_CONF_FILE), TCIAConf.class);

			AUTHORIZATION_HEADER = tciaConf.getAuthheader();
			String AUTHORIZATION_FLAG = tciaConf.getAuthflag();

			RESOURCE_URL = tciaConf.getBaseurl() + tciaConf.getResource();

			String authString = tciaConf.getUsername() + TCIAConstants.AUTH_SEPARATOR + tciaConf.getPassword();
			String encodedBytes = Base64.getEncoder().encodeToString(authString.getBytes());
			authValue = AUTHORIZATION_FLAG + TCIAConstants.AUTH_VAL_SEPARATOR + encodedBytes;
		} catch (IOException e) {
			logger.error("Exception in initializing the TCIA Client", e);
		}
	}


	/**
	 * Get the complete url of the resource
	 * @return the url of the resource
	 */
	public static String getResourceUrl() {
		return RESOURCE_URL;
	}


	/**
	 * Authenticate with the given authentication mechanism and return the image result.
	 * @param uriBuilder the URIBuilder object
	 * @return the ImageResult
	 * @throws URISyntaxException, if the given URI syntax is invalid
	 * @throws TCIAClientException, if TCIA client exception occurred
	 * @throws IOException, if an IO Exception occurred
	 */
	public static ImageResult authenticateAndGetImage(URIBuilder uriBuilder) throws URISyntaxException, TCIAClientException, IOException {
		URI uri = uriBuilder.build();
		// create a new HttpGet request
		HttpGet request = new HttpGet(uri);

		// add authentication header
		request.setHeader(TCIAClientUtil.getAuthorizationHeader(), TCIAClientUtil.getAuthValue());

		long startTime = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(request);
		long diff = System.currentTimeMillis() - startTime;

		logger.info("Server Response Received in " + diff + " ms");
		return getImageResult(uri, response);
	}


	/**
	 * Return a string in a specified output format from a URI Builder object
	 * @param format the output format
	 * @param uriBuilder the URI Builder object
	 * @return the string in the specified output format
	 * @throws URISyntaxException if the URI syntax is invalid.
	 * @throws TCIAClientException if the TCIA client throws an error.
	 * @throws IOException an IO Exception during the execution.
	 */
	public static String getStringFromURIBuilder(OutputFormat format, URIBuilder uriBuilder)
			throws URISyntaxException, TCIAClientException, IOException {
		uriBuilder.addParameter("format", format.name());

		URI uri = uriBuilder.build();
		InputStream is = getRawData(uri); // Get the raw data from the given uri
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}


	/**
	 * Save the image result object as a file.
	 * @param imageResult an instance of the ImageResult
	 * @param name the name of the file to be saved
	 * @param directory the directory to save the file
	 * @throws IOException if saving the image result as a file in the specified directory failed.
	 */
	public static void saveTo(ImageResult imageResult, String name, String directory) throws IOException
	{
		InputStream in = imageResult.getRawData();
		double averageDICOMFileSize = 200 * 1024d ; // 200KB
		double compressionRatio = 0.75 ; // approx
		int estimatedBytes = (int) (averageDICOMFileSize * compressionRatio * imageResult.getImageCount());

		FileOutputStream fos = new FileOutputStream(directory + "/" + name);
		byte[] buffer = new byte[4096];
		int read = -1;
		int sum = 0;
		while((read = in.read(buffer)) > 0)
		{
			fos.write(buffer , 0 , read);
			long mseconds = System.currentTimeMillis();
			sum += read;

			if(mseconds % 10 == 0)
			{
				logger.info(String.format("Bytes Written %s out of estimated %s  : " , sum , estimatedBytes));
			}
		}

		fos.close();
		in.close();
	}


	/*
	 * The private utility methods to be used by the public utility methods of this class.
	 */

	private static InputStream getRawData(URI uri) throws TCIAClientException, ClientProtocolException, IOException {
		// create a new HttpGet request
		HttpGet request = new HttpGet(uri);

		// add api_key to the header
		request.setHeader(AUTHORIZATION_HEADER, authValue);
		HttpResponse response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() != 200) // TCIA Server
		// error
		{
			return getStatus(uri, response);

		} else {
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.getContent() != null) {
				return entity.getContent();
			} else {
				throw new TCIAClientException(RESOURCE_URL, "No Content");
			}
		}
	}


	private static ImageResult getImageResult(URI uri, HttpResponse response) throws TCIAClientException, IOException {
		if (response.getStatusLine().getStatusCode() != 200) {
			getStatus(uri, response);
		} else {
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.getContent() != null) {
				ImageResult imageResult = new ImageResult();
				imageResult.setRawData(entity.getContent());
				imageResult.setImageCount(Integer.parseInt(response.getFirstHeader("imageCount").getValue()));
				return imageResult;
			} else {
				throw new TCIAClientException(TCIAClientUtil.getResourceUrl(), "No Content");
			}
		}
		return null;
	}


	private static InputStream getStatus(URI uri, HttpResponse response) throws TCIAClientException {
		if (response.getStatusLine().getStatusCode() == 401) // Unauthorized
		{
			throw new TCIAClientException(uri.toString(),
					"Unauthorized access");
		} else if (response.getStatusLine().getStatusCode() == 404) {
			throw new TCIAClientException(uri.toString(),
					"Resource not found");
		} else {
			throw new TCIAClientException(uri.toString(), "Server Error : "
					+ response.getStatusLine().getReasonPhrase());
		}
	}


	private static String getAuthValue() {
		return authValue;
	}


	private static String getAuthorizationHeader() {
		return AUTHORIZATION_HEADER;
	}
}
