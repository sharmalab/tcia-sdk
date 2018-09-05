package edu.emory.cci.tcia.client.test;

import static org.junit.Assert.fail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.emory.cci.tcia.client.util.ImageResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import edu.emory.cci.tcia.client.ITCIAClient;
import edu.emory.cci.tcia.client.OutputFormat;
import edu.emory.cci.tcia.client.TCIAClientException;
import edu.emory.cci.tcia.client.TCIAClientImpl;



/**
 * The core Test class to test the TCIA functionality with sample values, as JUnit tests.
 *  Refer https://wiki.cancerimagingarchive.net/display/Public/TCIA+Programmatic+Interface+%28REST+API%29+Usage+Guide
 */
public class TestTCIAClient {

	private static Logger logger = LogManager.getLogger(TestTCIAClient.class.getName());


	/**
	 *  Method : GetCollectionValues
	 *  Description : Returns  set of all collection values
	 */
	
	@Test
	public void testGetCollectionValues()
	{
		// create TCIA Client by passing API-Key and baseUrl in the constructor
		ITCIAClient client = new TCIAClientImpl();
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getCollectionValues(OutputFormat.json);

			logger.info("[GET COLLECTION VALUES]");
			// Print server response
			logger.info(respJSON);

		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}
	}
	
	/**
	 *  Method : GetImage
	 *  Description : Returns images in a zip file
	 */
	
	@Test
	public void testGetImage()
	{
		ITCIAClient client = new TCIAClientImpl();
		String seriesInstanceUID = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440";
		try {
			logger.info("[GET IMAGE]");
			// Make the RESTfull call . Response comes back as InputStream. 
			ImageResult imageResult = client.getImage(seriesInstanceUID);
			double averageDICOMFileSize = 200 * 1024d ; // 200KB
			double compressionRatio = 0.75 ; // approx
			int estimatedFileSize = (int) (averageDICOMFileSize * compressionRatio * imageResult.getImageCount());
			saveTo(imageResult.getRawData(),  seriesInstanceUID + ".zip", ".", estimatedFileSize);
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}
	}


	/**
	 *  Method : GetSingleImage
	 *  Description : Returns an image in a zip file
	 */

	@Test
	public void testGetSingleImage()
	{
		ITCIAClient client = new TCIAClientImpl();
		String sopInstanceUID = "1.3.6.1.4.1.14519.5.2.1.7695.4001.254637948180506182312529390348";
		String seriesInstanceUID = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440";
		try {
			logger.info("[GET SINGLE IMAGE]");
			// Make the RESTfull call . Response comes back as InputStream.
			ImageResult imageResult = client.getSingleImage(seriesInstanceUID, sopInstanceUID);
			double averageDICOMFileSize = 200 * 1024d ; // 200KB
			double compressionRatio = 0.75 ; // approx
			int estimatedFileSize = (int) (averageDICOMFileSize * compressionRatio * imageResult.getImageCount());
			saveTo(imageResult.getRawData(),  seriesInstanceUID + ".zip", ".", estimatedFileSize);

		} catch (TCIAClientException e) {
			fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}
	}

	@Test
	public void testGetSeries()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM"; // optional
		String studyInstanceUID = null; // "1.3.6.1.4.1.14519.5.2.1.7695.4001.130563880911723253267280582465"; // optional
		String modality = "MR"; // optional
		String patientID = null; // "TCGA-08-0244"; // optional
		String seriesInstanceUID = null; //"1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440"; // optional
		String bodyPartExamined = null; // "BRAIN"; // optional
		String manufacturer = "GE MEDICAL SYSTEMS"; // optional
		String manufacturerModelName = "GENESIS_SIGNA"; //optional

		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getSeries(collection, studyInstanceUID, modality, patientID, seriesInstanceUID,
					bodyPartExamined, manufacturer, manufacturerModelName, OutputFormat.json);

			logger.info("[GET SERIES]");

			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	
	@Test
	public void testGetPatientStudy()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM"; // optional
		String patientID = "TCGA-08-0244"; // optional
		String studyInstanceUID = "1.3.6.1.4.1.14519.5.2.1.7695.4001.130563880911723253267280582465"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getPatientStudy(collection, patientID, studyInstanceUID, OutputFormat.json);

			logger.info("[GET PATIENT STUDY]");
			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	@Test
	public void testGetPatient()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getPatient(collection , OutputFormat.json);

			logger.info("[GET PATIENT]");

			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetBodyPartValues()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM" ; // optional
		String modality = "MR"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getBodyPartValues(collection, modality, OutputFormat.json);

			logger.info("[GET BODY PART VALUES]");

			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetModalityValues()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM" ; // optional
		String bodyPartExamined = "BRAIN"; // optional

		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getModalityValues(collection, bodyPartExamined, OutputFormat.json);

			logger.info("[GET MODALITY VALUES]");

			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}
	
	@Test
	public void testGetManufacturerValues()
	{
		ITCIAClient client = new TCIAClientImpl();
		String collection = "TCGA-GBM" ; // optional
		String bodyPartExamined = "BRAIN"; // optional
		String modality = "MR"; // optional
		
		try {
			// Make the RESTfull call . Response comes back as InputStream. 
			String respJSON = client.getManufacturerValues(collection, bodyPartExamined, modality, OutputFormat.json);

			logger.info("[GET MANUFACTURER VALUES]");

			// Print server response
			logger.info(respJSON);
			
			
		} catch (TCIAClientException e) {
				fail(e.getMessage()); // request failed
		} catch (Exception e) {
			fail(e.getMessage()); // request failed
		}

	}

	
	
	private static void saveTo(InputStream in, String name, String directory, int estimatedBytes) throws IOException
	{
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
}
