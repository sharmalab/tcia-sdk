package edu.emory.cci.tcia.client;

import java.io.InputStream;
import java.net.URI;

import edu.emory.cci.tcia.client.util.ImageResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static edu.emory.cci.tcia.client.util.TCIAClientUtil.authenticateAndGetImage;
import static edu.emory.cci.tcia.client.util.TCIAClientUtil.getImageResult;
import static edu.emory.cci.tcia.client.util.TCIAClientUtil.getRawData;

import edu.emory.cci.tcia.client.util.TCIAClientUtil;

/**
 * The core class of the TCIA Client implementation
 */
public class TCIAClientImpl implements ITCIAClient {
	private static Logger logger = LogManager.getLogger(TCIAClientImpl.class.getName());

	private static String getImage = "getImage";
	private static String getManufacturerValues = "getManufacturerValues";
	private static String getModalityValues = "getModalityValues";
	private static String getCollectionValues = "getCollectionValues";
	private static String getBodyPartValues = "getBodyPartValues";
	private static String getPatientStudy = "getPatientStudy";
	private static String getSeries = "getSeries";
	private static String getPatient = "getPatient";
	private static String getSingleImage = "getSingleImage";





	/**
	 * The default constructor of the TCIA Client
	 */
	public TCIAClientImpl() {
		TCIAClientUtil.init();
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	/**
	 * Get the modality values
	 * @param collection the collection name
	 * @param bodyPartExamined the body part examined
	 * @param format the format
	 * @return the modality values
	 * @throws TCIAClientException if the execution fails
	 */
	public String getModalityValues(String collection, String bodyPartExamined,
	                                OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getModalityValues);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (bodyPartExamined != null)
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}

	}


	public String getManufacturerValues(String collection,
	                                    String bodyPartExamined, String modality, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getManufacturerValues);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (bodyPartExamined != null)
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);

			if (modality != null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	public String getCollectionValues(OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getCollectionValues);
			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	public String getBodyPartValues(String collection, String modality, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getBodyPartValues);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (modality != null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}

	}

	public String getPatientStudy(String collection, String patientID,
	                              String studyInstanceUID, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getPatientStudy);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (patientID != null)
				uriBuilder.addParameter(DICOMAttributes.PATIENT_ID, patientID);

			if (studyInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.STUDY_INSTANCE_UID, studyInstanceUID);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	public String getSeries(String collection, String studyInstanceUID, String modality, String patientID,
	                        String seriesInstanceUID, String bodyPartExamined, String manufacturer,
	                        String manufacturerModelName, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getSeries);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (studyInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.STUDY_INSTANCE_UID, studyInstanceUID);

			if (modality != null) {
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);
			}

			if (patientID != null) {
				uriBuilder.addParameter(DICOMAttributes.PATIENT_ID, patientID);
			}

			if (seriesInstanceUID != null) {
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);
			}

			if (bodyPartExamined != null) {
				uriBuilder.addParameter(DICOMAttributes.BODY_PART_EXAMINED, bodyPartExamined);
			}

			if (manufacturer != null) {
				uriBuilder.addParameter(DICOMAttributes.MANUFACTURER, manufacturer);
			}

			if (manufacturerModelName != null) {
				uriBuilder.addParameter(DICOMAttributes.MANUFACTURER_MODEL_NAME, manufacturerModelName);
			}

			uriBuilder.addParameter("format", format.name());
			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	public String getPatient(String collection, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getPatient);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}


	public String getSeriesSize(String seriesInstanceUID, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getPatient);

			if (seriesInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);

			uriBuilder.addParameter("format", format.name());

			URI uri = uriBuilder.build();
			InputStream is = getRawData(uri);
			return convertStreamToString(is);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}


	public ImageResult getImage(String seriesInstanceUID)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getImage);

			if (seriesInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);


			return authenticateAndGetImage(uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	public ImageResult getSingleImage(String seriesInstanceUID, String sopInstanceUID)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getSingleImage);

			if (seriesInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);

			if (sopInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.SOP_INSTANCE_UID, sopInstanceUID);

			return authenticateAndGetImage(uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}


	public String NewStudiesInPatientCollection(String collection, OutputFormat format) throws TCIAClientException {
		return null;
	}

	public String getSOPInstanceUIDs(String collection, OutputFormat format) throws TCIAClientException {
		return null;
	}

	public String PatientsByModality(String collection, String patientID, String studyInstanceUID, OutputFormat format) throws TCIAClientException {
		return null;
	}

	public String NewPatientsInCollection(String collection, String patientID, String studyInstanceUID, OutputFormat format) throws TCIAClientException {
		return null;
	}

	public String getSharedList(String collection, String patientID, String studyInstanceUID, OutputFormat format) throws TCIAClientException {
		return null;
	}
}
