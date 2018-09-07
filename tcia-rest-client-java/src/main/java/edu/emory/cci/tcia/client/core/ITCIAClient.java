package edu.emory.cci.tcia.client.core;

import edu.emory.cci.tcia.client.exceptions.TCIAClientException;
import edu.emory.cci.tcia.client.util.ImageResult;

/**
 * The Interface consisting of the method definitions for the TCIA client
 */
public interface ITCIAClient {
	String getModalityValues(String collection, String bodyPartExamined, OutputFormat format) throws TCIAClientException;
	String getManufacturerValues(String collection, String bodyPartExamined, String modality, OutputFormat format) throws TCIAClientException;
	String getCollectionValues(OutputFormat format) throws TCIAClientException;
	String getBodyPartValues(String collection, String modality, OutputFormat format) throws TCIAClientException;
	String getPatientStudy(String collection, String patientID, String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	String getSeries(String collection, String studyInstanceUID, String modality, String patientID,
	                        String seriesInstanceUID, String bodyPartExamined, String manufacturer,
	                        String manufacturerModelName, OutputFormat format) throws TCIAClientException;
	String getPatient(String collection, OutputFormat format) throws TCIAClientException;
	ImageResult getImage(String seriesInstanceUID) throws TCIAClientException;
	ImageResult getSingleImage(String seriesInstanceUID, String sopInstanceUID) throws TCIAClientException;
	String getSeriesSize(String seriesInstanceUID, OutputFormat format) throws TCIAClientException;

	String NewStudiesInPatientCollection(String date, String collection, String patientID, OutputFormat format) throws TCIAClientException;
	String getSOPInstanceUIDs(String seriesInstanceUID, OutputFormat format) throws TCIAClientException;
	String PatientsByModality(String collection, String modality, OutputFormat format) throws TCIAClientException;
	String NewPatientsInCollection(String date,String collection, OutputFormat format) throws TCIAClientException;
	String getSharedList(String name, OutputFormat format) throws TCIAClientException;


}
