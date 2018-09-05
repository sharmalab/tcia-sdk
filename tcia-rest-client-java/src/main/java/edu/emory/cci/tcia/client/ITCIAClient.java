package edu.emory.cci.tcia.client;

import edu.emory.cci.tcia.client.util.ImageResult;

/**
 * The Interface consisting of the method definitions for the TCIA client
 */
public interface ITCIAClient {
	public String getModalityValues(String collection, String bodyPartExamined, OutputFormat format) throws TCIAClientException;
	public String getManufacturerValues(String collection, String bodyPartExamined, String modality, OutputFormat format) throws TCIAClientException;
	public String getCollectionValues(OutputFormat format) throws TCIAClientException;
	public String getBodyPartValues(String collection, String modality, OutputFormat format) throws TCIAClientException;
	public String getPatientStudy(String collection, String patientID, String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	public String getSeries(String collection, String studyInstanceUID, String modality, String patientID,
	                        String seriesInstanceUID, String bodyPartExamined, String manufacturer,
	                        String manufacturerModelName, OutputFormat format) throws TCIAClientException;
	public String getPatient(String collection, OutputFormat format) throws TCIAClientException;
	public ImageResult getImage(String seriesInstanceUID) throws TCIAClientException;
	public ImageResult getSingleImage(String seriesInstanceUID, String sopInstanceUID) throws TCIAClientException;
	public String getSeriesSize(String seriesInstanceUID, OutputFormat format) throws TCIAClientException;

	public String NewStudiesInPatientCollection(String collection, OutputFormat format) throws TCIAClientException;
	public String getSOPInstanceUIDs(String collection, OutputFormat format) throws TCIAClientException;
	public String PatientsByModality(String collection,String patientID , String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	public String NewPatientsInCollection(String collection,String patientID , String studyInstanceUID, OutputFormat format) throws TCIAClientException;
	public String getSharedList(String collection,String patientID , String studyInstanceUID, OutputFormat format) throws TCIAClientException;


}
