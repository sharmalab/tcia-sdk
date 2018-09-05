package edu.emory.cci.tcia.client;

import java.io.InputStream;
import java.net.URI;

import edu.emory.cci.tcia.client.util.ImageResult;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.emory.cci.tcia.client.util.TCIAClientUtil;

import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getBodyPartValues;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getCollectionValues;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getImage;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getManufacturerValues;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getModalityValues;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getPatient;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getPatientStudy;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getSeries;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getSeriesSize;
import static edu.emory.cci.tcia.client.conf.EndPointDefinitions.getSingleImage;
import static edu.emory.cci.tcia.client.util.TCIAClientUtil.authenticateAndGetImage;
import static edu.emory.cci.tcia.client.util.TCIAClientUtil.getRawData;

/**
 * The core class of the TCIA Client implementation
 */
public class TCIAClientImpl implements ITCIAClient {
	private static Logger logger = LogManager.getLogger(TCIAClientImpl.class.getName());

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
	 * @param collection the collection name : optional
 	 * @param bodyPartExamined the body part examined : optional
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


	/**
	 * Get the manufacturer Values
	 * @param collection the name of the collection : optional
	 * @param bodyPartExamined the examined body part : optional
	 * @param modality the modality : optional
	 * @param format the format
	 * @return the manufacturer values
	 * @throws TCIAClientException if the execution failed
	 */
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

	/**
	 * Get the values of the collections
	 * @param format the format to return the output
	 * @return the collection values
	 * @throws TCIAClientException, if the execution failed.
	 */
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

	/**
	 * Get the body part values
	 * @param collection the name of the collection : optional
	 * @param modality the modality : optional
	 * @param format the output format
	 * @return the body part values
	 * @throws TCIAClientException, if the execution failed
	 */
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

	/**
	 * Get the relevant patient studies
	 * @param collection the collection name : optional
	 * @param patientID the ID of the patient : optional
	 * @param studyInstanceUID the UID of the study instance : optional
	 * @param format the output format
	 * @return the patient study
	 * @throws TCIAClientException, if the execution failed
	 */
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

	/**
	 * Get the series
	 * @param collection the collection name : optional
	 * @param studyInstanceUID the UID of the study instance : optional
 	 * @param modality the modality : optional
	 * @param patientID ID of the patient : optional
	 * @param seriesInstanceUID UID of the series instance : optional
 	 * @param bodyPartExamined the examined body part : optional
 	 * @param manufacturer name of the manufacturer : optional
	 * @param manufacturerModelName the model of the manufacturer : optional
	 * @param format the output format
	 * @return the series
	 * @throws TCIAClientException, if the execution failed
	 */
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

	/**
	 * Get the patients
	 * @param collection, the collection name : optional
	 * @param format, the output format
	 * @return the patients
	 * @throws TCIAClientException, if the execution failed
	 */
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


	/**
	 * Get the size of the given series
	 * @param seriesInstanceUID : UID of the series instance : mandatory
	 * @param format, the output format
	 * @return the size of the series
	 * @throws TCIAClientException, if the execution failed
	 */
	public String getSeriesSize(String seriesInstanceUID, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getSeriesSize);

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


	/**
	 * Get a zip of matching images
	 * @param seriesInstanceUID, the UID of the series instance. : mandatory
	 * @return the zip of images
	 * @throws TCIAClientException, if the execution failed
	 */
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

	/**
	 * Get a single image
	 * @param seriesInstanceUID, the UID of the series instance. : mandatory
	 * @param sopInstanceUID, the UID of the Service-Object Pair (SOP). : mandatory
	 * @return the single image
	 * @throws TCIAClientException, if the execution failed
	 */
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
