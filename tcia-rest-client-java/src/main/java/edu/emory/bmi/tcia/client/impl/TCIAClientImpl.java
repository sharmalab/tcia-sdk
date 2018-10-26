package edu.emory.bmi.tcia.client.impl;

import java.net.URI;

import edu.emory.bmi.tcia.client.core.ITCIAClient;
import edu.emory.bmi.tcia.client.core.OutputFormat;
import edu.emory.bmi.tcia.client.util.ImageResult;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.emory.bmi.tcia.client.definitions.DICOMAttributes;
import edu.emory.bmi.tcia.client.exceptions.TCIAClientException;
import edu.emory.bmi.tcia.client.util.TCIAClientUtil;

/*
 * Static imports of the service endpoint definitions
 */
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.NewPatientsInCollection;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.NewStudiesInPatientCollection;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.PatientsByModality;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getBodyPartValues;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getCollectionValues;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getImage;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getManufacturerValues;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getModalityValues;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getPatient;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getPatientStudy;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getSOPInstanceUIDs;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getSeries;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getSeriesSize;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getSharedList;
import static edu.emory.bmi.tcia.client.definitions.ServiceEndpoints.getSingleImage;

/*
 * Static imports of the utility methods.
 */
import static edu.emory.bmi.tcia.client.util.TCIAClientUtil.authenticateAndGetImage;
import static edu.emory.bmi.tcia.client.util.TCIAClientUtil.getStringFromURIBuilder;


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


	/**
	 * Get the modality values
	 *
	 * @param collection       the collection name : optional
	 * @param bodyPartExamined the body part examined : optional
	 * @param format           the format
	 * @return the modality values
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

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}


	/**
	 * Get the manufacturer Values
	 *
	 * @param collection       the name of the collection : optional
	 * @param bodyPartExamined the examined body part : optional
	 * @param modality         the modality : optional
	 * @param format           the format
	 * @return the manufacturer values
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

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the values of the collections
	 *
	 * @param format the format to return the output
	 * @return the collection values
	 */
	public String getCollectionValues(OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getCollectionValues);
			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the body part values
	 *
	 * @param collection the name of the collection : optional
	 * @param modality   the modality : optional
	 * @param format     the output format
	 * @return the body part values
	 */
	public String getBodyPartValues(String collection, String modality, OutputFormat format) throws TCIAClientException {
		return getModalityProperties(collection, modality, format, getBodyPartValues);
	}

	/**
	 * Get the relevant patient studies
	 *
	 * @param collection       the collection name : optional
	 * @param patientID        the ID of the patient : optional
	 * @param studyInstanceUID the UID of the study instance : optional
	 * @param format           the output format
	 * @return the patient study
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

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the series
	 *
	 * @param collection            the collection name : optional
	 * @param studyInstanceUID      the UID of the study instance : optional
	 * @param modality              the modality : optional
	 * @param patientID             ID of the patient : optional
	 * @param seriesInstanceUID     UID of the series instance : optional
	 * @param bodyPartExamined      the examined body part : optional
	 * @param manufacturer          name of the manufacturer : optional
	 * @param manufacturerModelName the model of the manufacturer : optional
	 * @param format                the output format
	 * @return the series
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

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the patients
	 *
	 * @param collection, the collection name : optional
	 * @param format,     the output format
	 * @return the patients
	 */
	public String getPatient(String collection, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getPatient);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}


	/**
	 * Get the size of the given series
	 *
	 * @param seriesInstanceUID : UID of the series instance : mandatory
	 * @param format,           the output format
	 * @return the size of the series
	 */
	public String getSeriesSize(String seriesInstanceUID, OutputFormat format) throws TCIAClientException {
		return getSeriesProperties(seriesInstanceUID, format, getSeriesSize);
	}


	/**
	 * Get a zip of matching images
	 *
	 * @param seriesInstanceUID, the UID of the series instance. : mandatory
	 * @return the zip of images
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
	 *
	 * @param seriesInstanceUID, the UID of the series instance. : mandatory
	 * @param sopInstanceUID,    the UID of the Service-Object Pair (SOP). : mandatory
	 * @return the single image
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


	/**
	 * Gets the new studies in a patient collection
	 *
	 * @param date       the date of the studies  : mandatory
	 * @param collection the collection name : mandatory
	 * @param patientID  the ID of the patient : optional
	 * @param format     the output format
	 * @return the new studies in the patient collection
	 */
	public String NewStudiesInPatientCollection(String date, String collection, String patientID, OutputFormat format)
			throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + NewStudiesInPatientCollection);

			if (date != null)
				uriBuilder.addParameter(DICOMAttributes.DATE, date);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.PATIENT_ID, patientID);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Gets the SOP (Service Object Pair) Instance UIDs
	 *
	 * @param seriesInstanceUID the service instance UIDs : mandatory
	 * @param format            the output format
	 * @return the SOP Instance UIDs
	 */
	public String getSOPInstanceUIDs(String seriesInstanceUID, OutputFormat format) throws TCIAClientException {
		return getSeriesProperties(seriesInstanceUID, format, getSOPInstanceUIDs);
	}


	private String getSeriesProperties(String seriesInstanceUID, OutputFormat format, String propertiesOfSeriesInstance) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + propertiesOfSeriesInstance);

			if (seriesInstanceUID != null)
				uriBuilder.addParameter(DICOMAttributes.SERIES_INSTANCE_UID, seriesInstanceUID);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the patients by modality
	 *
	 * @param collection the name of the collection : mandatory
	 * @param modality   the modality : mandatory
	 * @param format     the output format
	 * @return the patients by modality
	 */

	public String PatientsByModality(String collection, String modality, OutputFormat format) throws TCIAClientException {
		return getModalityProperties(collection, modality, format, PatientsByModality);
	}

	private String getModalityProperties(String collection, String modality, OutputFormat format, String propertiesOfModality) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + propertiesOfModality);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			if (modality != null)
				uriBuilder.addParameter(DICOMAttributes.MODALITY, modality);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the new patients in collection
	 *
	 * @param date       the given date : mandatory
	 * @param collection the collection name : mandatory
	 * @param format     the output format
	 * @return the new patients in collection
	 */
	public String NewPatientsInCollection(String date, String collection, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + NewPatientsInCollection);

			if (date != null)
				uriBuilder.addParameter(DICOMAttributes.DATE, date);

			if (collection != null)
				uriBuilder.addParameter(DICOMAttributes.COLLECTION, collection);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}

	/**
	 * Get the contents of a shared list
	 *
	 * @param name   the name of the shared list : mandatory
	 * @param format the output format
	 * @return the contents of the shared list
	 */
	public String getSharedList(String name, OutputFormat format) throws TCIAClientException {
		try {
			URI baseUri = new URI(TCIAClientUtil.getResourceUrl());
			URIBuilder uriBuilder = new URIBuilder(baseUri.toString() + "/" + getSharedList);

			if (name != null)
				uriBuilder.addParameter(DICOMAttributes.NAME, name);

			return getStringFromURIBuilder(format, uriBuilder);

		} catch (TCIAClientException e) {
			throw e;
		} catch (Exception e) {
			throw new TCIAClientException(e, TCIAClientUtil.getResourceUrl());
		}
	}
}
