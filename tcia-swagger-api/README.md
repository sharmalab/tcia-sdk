# TCIA Swagger API Definitions

TCIA Swagger API definitions were created using the Swagger Inspector and SwaggerHub.

The steps followed:

Run the REST endpoint in Swagger Inspector.

Create Open API Definition by "Go to SwaggerHub" and Import OpenAPI.

Now, export to YAML from the SwaggerHub.

Update the auto-generated YAML with the relevant service descriptions.

Deploy the API definitions in your Swagger-UI local installations.

Currently we run Swagger with Docker:

$ docker run -p 80:8080 -e "SWAGGER_JSON=/tcia.json" -v /home/ex-pradeebankathira/tcia.json:/tcia.json swaggerapi/swagger-ui &


It appears that it is not possible to pass a YAML file at startup time to open the Swagger API with that.

Therefore, we open the final YAML file with SwaggerHub and export it as JSON to start our Swagger instance with the JSON file.


![The TCIA Swagger APIs](https://user-images.githubusercontent.com/225631/44429294-ef9a9c80-a564-11e8-9332-d6fb6234c036.png)
