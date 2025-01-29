## Code for ACP Tutorial 1

A basic service that will allow the user to perform actions regarding registering ACP students for class. The registered students will be placed into BLOB storage. 

I have included an env.env file here and hardcoded the environment variables into the Dockerfile (please do not do this normally) this is just to to save time when it comes to cloning and getting the service up and running :)

## Getting it working

1) Clone the repo.
2) Open up in Intellij.
3) Ensure that Intellij will use the env.env file for the environment variables. 
4) If you come across issues with lombok, make sure Intellij is looking in the right place for the processors (settings -> Build, Execution, Deployment -> compiler -> annotation processors -> obtain processors from object classpath)
5) Run the application locally (send a request or two). 
6) Clean and Package. 
7) Build the image using the Dockerfile. 
8) Run the image (send some more requests). 
9) Place the image into a tar file. Then reload it. 
10) Rerun the image (ensure that everything works).

