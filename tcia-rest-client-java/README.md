Java Client
================================================
This project has a dependency on Apache HttpClient.
 
For more information visit http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html#d5e43

* Import tcia-rest-client-java as a maven project in an IDE that offers Maven integration, such as IntelliJ IDEA or Eclipse.

* Test cases can be found under tcia-rest-client-java/src/test/java

This project requires JDK 1.8 for compiling and running. It does not work with later or earlier versions.


## Configuring Authentication

Create config.yaml in your execution directory with the correct access credentials (TCIA user name and password) 
to access the TCIA REST API with authentication. 

A sample configuration file can be found at src/main/resources/config.test.yaml.

The currently implemented methods query the public data sets. Therefore, the user name and password are optional for now.
You may therefore create your configuration file based on the simple configuration file that can be found at 
src/main/resources/config.simple.test.yaml instead.

You may build your code with the tests, following the command: mvn clean install


## Using TCIA-SDK as a third-party module in your project

Add the below to your project's parent pom.

      
      <dependency>
          <groupId>edu.emory.bmi</groupId>
          <artifactId>tcia-sdk</artifactId>
      </dependency>
      
      
Make sure to include the authentication details in config.yaml as above, in your project's src/main/resources folder.
