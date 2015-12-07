The c2assistants code has been developed for generating suggestions and content for games created under the FP7 project C2Learn (no. 318480).

Guidelines for the c2assistants webservice:

-- TO DEPLOY:
	In order to deploy the webservice (provided as-is), the process is quite straightforward. Simply log on to a Tomcat server as administrator, which brings you to the Tomcat Web Application Manager with a list of installed web services. Browse down to 'Deploy->WAR file to deploy' and upload the IDGWeb.war (located in the document root) and press Deploy. The webservice will then be deployed online.
	Please note that since the webservice can create typical icons for concepts it has not in its database already, you will need to change the folder permissions to allow the public to 'Write' within the folder containing the webservice. Note that this only affects Typical Tom and Progressive Petra, and the webservice will work with all the other c2assistants regardless.
	Edit the idgWebParameters.xml in order to customize where files are located and are being saved, as well as other parameters.
	
-- TO COMPILE:
	In order to compile, the provided codebase includes a Netbeans project information. Therefore, opening it directly in Netbeans ( https://netbeans.org/ ) is straightforward. Note that it is necessary to include the libraries found in the lib folder ( woxSerializer.jar and jdom-2.0.5.jar ). The project has been built using Maven for handling external libraries. Once the project is compiled, it will generate or update the IDGWeb.war file, which you can deploy using the guidelines above.
