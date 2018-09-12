
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
    
    def get_modality_values(self,collection = None , bodyPartExamined = None, outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_MODALITY_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    
    def get_manufacturer_values(self,collection = None , bodyPartExamined = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_MANUFACTURER_VALUES
        queryParameters = {"Collection" : collection , "BodyPartExamined" : bodyPartExamined , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
        
    def get_collection_values(self,outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_COLLECTION_VALUES
        queryParameters = { "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
        
    def get_body_part_values(self,collection = None , modality = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_BODY_PART_VALUES
        queryParameters = {"Collection" : collection , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp

    def get_patient_study(self,collection = None , patientId = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_PATIENT_STUDY
        queryParameters = {"Collection" : collection , "PatientID" : patientId , "StudyInstanceUID" : studyInstanceUid , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_series(self, collection = None , modality = None , studyInstanceUid = None , outputFormat = "json" ):
        serviceUrl = self.baseUrl + "/" + self.GET_SERIES
        queryParameters = {"Collection" : collection , "StudyInstanceUID" : studyInstanceUid , "Modality" : modality , "format" : outputFormat }
        resp = self.execute(serviceUrl , queryParameters)
        return resp
    def get_patient(self,collection = None , outputFormat = "json" ):
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
    
                
        

def printServerResponse(method, response):
    if response.getcode() == 200:
        print (method + ": The server returned:\n")
        print (response.read())
        print ("\n")
    
    else:
        print ("Error : " + str(response.getcode)) # print error code

# Instantiate TCIAClient object
tcia_client = TCIAClient(credentials = sys.argv[2] , baseUrl = sys.argv[1])  # Set the API-Key

# test get_manufacturer_values
try:    
    response = tcia_client.get_manufacturer_values(collection = "TCGA-GBM", bodyPartExamined = "BRAIN", modality ="MR", outputFormat = "json")
    printServerResponse(tcia_client.GET_MANUFACTURER_VALUES, response);
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_MANUFACTURER_VALUES + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_modality_values
try:    
    response = tcia_client.get_modality_values(collection = "TCGA-GBM", bodyPartExamined = "BRAIN", outputFormat = "json")
    printServerResponse(tcia_client.GET_MODALITY_VALUES, response);
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_MODALITY_VALUES + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_body_part_values
try:    
    response = tcia_client.get_body_part_values(collection = "TCGA-GBM", modality = "MR", outputFormat = "json")
    printServerResponse(tcia_client.GET_BODY_PART_VALUES, response);
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_BODY_PART_VALUES + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_collection_values
try:    
    response = tcia_client.get_collection_values(outputFormat = "json")
    printServerResponse(tcia_client.GET_COLLECTION_VALUES, response);
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_COLLECTION_VALUES + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())

# test get_image
try:
    response = tcia_client.get_image(seriesInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440");
    # Save server response as images.zip in current directory
    if response.getcode() == 200:
        print ("\n" + str(response.info()))
        bytesRead = response.read()
        fout = open("images.zip", "wb")
        fout.write(bytesRead)
        fout.close()
        print ("\nDownloaded file images.zip from the server")
    else:
        print ("Error : " + str(response.getcode)) # print error code
        print ("\n" + str(response.info()))
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_IMAGE + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())


# test get_single_image
try:
    response = tcia_client.get_single_image(sopInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.254637948180506182312529390348", seriesInstanceUid = "1.3.6.1.4.1.14519.5.2.1.7695.4001.306204232344341694648035234440");
    # Save server response as images.zip in current directory
    if response.getcode() == 200:
        print ("\n" + str(response.info()))
        bytesRead = response.read()
        fout = open("images.zip", "wb")
        fout.write(bytesRead)
        fout.close()
        print ("\nDownloaded file images.zip from the server")
    else:
        print ("Error : " + str(response.getcode)) # print error code
        print ("\n" + str(response.info()))
except urllib.error.HTTPError as err:
    print ("Error executing " + tcia_client.GET_SINGLE_IMAGE + ":\nError Code: ", str(err.code) , "\nMessage: " , err.read())    