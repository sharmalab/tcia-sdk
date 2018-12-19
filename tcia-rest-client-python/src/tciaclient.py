
import urllib.request
import urllib.parse
import sys
#
# Refer https://wiki.cancerimagingarchive.net/display/Public/REST+API+Usage+Guide for complete list of API
#


class TCIAClient:
    GET_IMAGE = "getImage"
    GET_SINGLE_IMAGE = "getSingleImage"
    GET_MANUFACTURER_VALUES = "getManufacturerValues"
    GET_MODALITY_VALUES = "getModalityValues"
    GET_COLLECTION_VALUES = "getCollectionValues"
    GET_BODY_PART_VALUES = "getBodyPartValues"
    GET_PATIENT_STUDY = "getPatientStudy"
    GET_SERIES = "getSeries"
    GET_PATIENT = "getPatient"    
    NEW_STUDIES_IN_PATIENT_COLLECTION = "NewStudiesInPatientCollection"
    GET_SOP_INSTANCE_UIDS = "getSOPInstanceUIDs"
    PATIENTS_BY_MODALITY = "PatientsByModality"
    GET_SERIES_SIZE = "getSeriesSize"
    NEW_PATIENTS_IN_COLLECTION = "NewPatientsInCollection"
    GET_SHARED_LIST = "getSharedList"

    def __init__(self, credentials , baseUrl):
        self.credentials = credentials
        self.baseUrl = baseUrl
        
    def execute(self, url, queryParameters={}):
        queryParameters = dict((k, v) for k, v in queryParameters.items() if v)
        credentialsHeader = "ldap" + " " + self.credentials
        headers = {"Authorization" : credentialsHeader}
        queryString = "?%s" % urllib.parse.urlencode(queryParameters)
        requestUrl = url + queryString
        request = urllib.request.Request(url=requestUrl , headers=headers)
        resp = urllib.request.urlopen(request)
        return resp
    
    def get_modality_values(self, collection = None, bodyPartExamined=None, outputFormat="json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_MODALITY_VALUES
        queryParameters = {"Collection": collection, "BodyPartExamined" : bodyPartExamined , "format": outputFormat}
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    
    def get_manufacturer_values(self, collection=None, bodyPartExamined=None, modality=None, outputFormat="json"):
        serviceUrl = self.baseUrl + "/" + self.GET_MANUFACTURER_VALUES
        queryParameters = {"Collection": collection, "BodyPartExamined" : bodyPartExamined , "Modality": modality,
                           "format": outputFormat }
        resp = self.execute(serviceUrl, queryParameters)
        return resp
        
    def get_collection_values(self,outputFormat="json"):
        serviceUrl = self.baseUrl + "/" + self.GET_COLLECTION_VALUES
        queryParameters = { "format": outputFormat}
        resp = self.execute(serviceUrl, queryParameters)
        return resp
        
    def get_body_part_values(self,collection = None , modality = None , outputFormat="json"):
        serviceUrl = self.baseUrl + "/" + self.GET_BODY_PART_VALUES
        queryParameters = {"Collection": collection, "Modality": modality, "format": outputFormat}
        resp = self.execute(serviceUrl, queryParameters)
        return resp

    def get_patient_study(self,collection = None , patientId = None , studyInstanceUid = None , outputFormat="json"):
        serviceUrl = self.baseUrl + "/" + self.GET_PATIENT_STUDY
        queryParameters = {"Collection": collection, "PatientID": patientId, "StudyInstanceUID": studyInstanceUid,
                           "format": outputFormat }
        resp = self.execute(serviceUrl, queryParameters)
        return resp

    def get_series(self, collection=None, studyInstanceUid=None, modality=None, patientID=None,
                   seriesInstanceUid=None, bodyPartExamined=None, manufacturer=None, manufacturerModelName=None,
                   outputFormat="json"):
        serviceUrl = self.baseUrl + "/" + self.GET_SERIES
        queryParameters = {"Collection": collection, "StudyInstanceUID": studyInstanceUid, "Modality": modality,
                           "SeriesInstanceUID": seriesInstanceUid, "BodyPartExamined": bodyPartExamined,
                           "Manufacturer": manufacturer, "ManufacturerModelName": manufacturerModelName, "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_patient(self, collection = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_PATIENT
        queryParameters = {"Collection" : collection , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_image(self, seriesInstanceUid):   # SeriesInstanceUID: required
        serviceUrl = self.baseUrl + "/" + self.GET_IMAGE
        queryParameters = { "SeriesInstanceUID" : seriesInstanceUid }
        resp = self.execute( serviceUrl , queryParameters)
        return resp

    def get_single_image(self, sopInstanceUid, seriesInstanceUid):   # SeriesInstanceUID: required, sopInstanceUID: required
        serviceUrl = self.baseUrl + "/" + self.GET_SINGLE_IMAGE
        queryParameters = { "SOPInstanceUID" : sopInstanceUid, "SeriesInstanceUID" : seriesInstanceUid}        
        resp = self.execute( serviceUrl , queryParameters)
        return resp        
    
    def new_studies_in_patient_collection(self, collection, date, patientId = None, outputFormat = "json"):   # collection: required, date: required
        serviceUrl = self.baseUrl + "/" + self.NEW_STUDIES_IN_PATIENT_COLLECTION
        queryParameters = { "Collection" : collection, "Date" : date, "PatientID": patientId, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp


    def get_sop_instance_uids(self, seriesInstanceUid, outputFormat = "json"):   # SeriesInstanceUID: required
        serviceUrl = self.baseUrl + "/" + self.GET_SOP_INSTANCE_UIDS
        queryParameters = { "SeriesInstanceUID" : seriesInstanceUid, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp

                
    def patients_by_modality(self, collection, modality, outputFormat = "json"):   # collection: required, modality: required
        serviceUrl = self.baseUrl + "/" + self.PATIENTS_BY_MODALITY
        queryParameters = { "Collection" : collection, "Modality" : modality, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp


    def get_series_size(self, seriesInstanceUid, outputFormat = "json"):   # SeriesInstanceUID: required
        serviceUrl = self.baseUrl + "/" + self.GET_SERIES_SIZE
        queryParameters = { "SeriesInstanceUID" : seriesInstanceUid, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp

     
    def new_patients_in_collection(self, collection, date, outputFormat = "json"):   # collection: required, date: required
        serviceUrl = self.baseUrl + "/" + self.NEW_PATIENTS_IN_COLLECTION
        queryParameters = { "Collection" : collection, "Date" : date, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp

    def get_shared_list(self, name, outputFormat = "json"):   # name: required
        serviceUrl = self.baseUrl + "/" + self.GET_SHARED_LIST
        queryParameters = { "name" : name, "format" : outputFormat}
        resp = self.execute( serviceUrl , queryParameters)
        return resp


def printServerResponse(method, response):
    if response.getcode() == 200:
        print (method + ": The server returned:\n")
        print (response.read())
        print ("\n")
    
    else:
        print ("Error : " + str(response.getcode)) # print error code


# Instantiate TCIAClient object
tcia_client = TCIAClient(credentials = sys.argv[2] , baseUrl = sys.argv[1])  # Set the API-Key


# test get_shared_list
try:    
    response = tcia_client.get_shared_list(name = "test", outputFormat = "json")
    printServerResponse(tcia_client.GET_SHARED_LIST, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_SHARED_LIST + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test new_patients_in_collection
try:    
    response = tcia_client.new_patients_in_collection(collection = "TCGA-GBM", date = "1998-12-08", outputFormat = "json")
    printServerResponse(tcia_client.NEW_PATIENTS_IN_COLLECTION, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.NEW_PATIENTS_IN_COLLECTION + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_series_size
try:    
    response = tcia_client.get_series_size(seriesInstanceUid ="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440", outputFormat = "json")
    printServerResponse(tcia_client.GET_SERIES_SIZE, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_SERIES_SIZE + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test patients_by_modality
try:    
    response = tcia_client.patients_by_modality(collection ="TCGA-GBM", modality="MR", outputFormat = "json")
    printServerResponse(tcia_client.PATIENTS_BY_MODALITY, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.PATIENTS_BY_MODALITY + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_sop_instance_uids
try:    
    response = tcia_client.get_sop_instance_uids(seriesInstanceUid ="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440", outputFormat = "json")
    printServerResponse(tcia_client.GET_SOP_INSTANCE_UIDS, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_SOP_INSTANCE_UIDS + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test new_studies_in_patient_collection
try:    
    response = tcia_client.new_studies_in_patient_collection(collection = "TCGA-GBM", date = "1998-12-08", patientId ="TCGA-08-0244", outputFormat = "json")
    printServerResponse(tcia_client.NEW_STUDIES_IN_PATIENT_COLLECTION, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.NEW_STUDIES_IN_PATIENT_COLLECTION + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_manufacturer_values
try:    
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM", bodyPartExamined = "BRAIN", modality ="MR", outputFormat="json")
    printServerResponse(tcia_client.GET_MANUFACTURER_VALUES, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_MANUFACTURER_VALUES + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_modality_values
try:    
    response = tcia_client.get_modality_values(collection = "TCGA-GBM", bodyPartExamined = "BRAIN", outputFormat="json")
    printServerResponse(tcia_client.GET_MODALITY_VALUES, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_MODALITY_VALUES + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_patient_study
try:    
    response = tcia_client.get_patient_study(collection = "TCGA-GBM", patientId = "TCGA-08-0244", studyInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.130563880911723253267280582465", outputFormat = "json")
    printServerResponse(tcia_client.GET_PATIENT_STUDY, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_PATIENT_STUDY + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_body_part_values
try:    
    response = tcia_client.get_body_part_values(collection = "TCGA-GBM", modality = "MR", outputFormat = "json")
    printServerResponse(tcia_client.GET_BODY_PART_VALUES, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_BODY_PART_VALUES + ":\nError Code: ", str(err.code), "\nMessage: ",
          err.read())


# test get_patient
try:    
    response = tcia_client.get_patient(collection = "TCGA-GBM", outputFormat = "json")
    printServerResponse(tcia_client.GET_PATIENT, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_PATIENT + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_series
try:    
    response = tcia_client.get_series(collection = "TCGA-GBM", modality = "MR", manufacturer="GE MEDICAL SYSTEMS",
                                      manufacturerModelName="GENESIS_SIGNA", outputFormat="json")
    printServerResponse(tcia_client.GET_SERIES, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_SERIES + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_collection_values
try:    
    response = tcia_client.get_collection_values(outputFormat = "json")
    printServerResponse(tcia_client.GET_COLLECTION_VALUES, response);
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_COLLECTION_VALUES + ":\nError Code: ", str(err.code), "\nMessage: ",
          err.read())

# test get_image
try:
    response = tcia_client.get_image(
        seriesInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440")
    # Save server response as images.zip in current directory
    if response.getcode() == 200:
        print("\n" + str(response.info()))
        bytesRead = response.read()
        fout = open("images.zip", "wb")
        fout.write(bytesRead)
        fout.close()
        print("\nDownloaded file images.zip from the server")
    else:
        print("Error : " + str(response.getcode)) # print error code
        print("\n" + str(response.info()))
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_IMAGE + ":\nError Code: ", str(err.code), "\nMessage: ", err.read())


# test get_single_image
try:
    response = tcia_client.get_single_image(
        sopInstanceUid="1.3.6.1.4.1.14519.5.2.1.7695.4001.254637948180506182312529390348",
        seriesInstanceUid="1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440")
    # Save server response as images.zip in current directory
    if response.getcode() == 200:
        print("\n" + str(response.info()))
        bytesRead = response.read()
        fout = open("images.zip", "wb")
        fout.write(bytesRead)
        fout.close()
        print("\nDownloaded file images.zip from the server")
    else:
        print("Error : " + str(response.getcode))  # print error code
        print("\n" + str(response.info()))
except urllib.error.HTTPError as err:
    print("Error executing " + tcia_client.GET_SINGLE_IMAGE + ":\nError Code: ", str(err.code),
          "\nMessage: ", err.read())
