# Image Compression Server
Simple Java HTTP server with image compression (JPG only) and EXIF cleaner.

Compression is achieved by using standard Java library - `javax.imageio`

## Tech stack:
* Java 21
* Gradle
* TinyLog

## Requirements
* Java 21

## Usage
```bash
# Start the HTTP server using the command:
$ java -jar image-compression-server-1.0-all.jar

# Using cURL to transfer the image
$ curl -X POST --data-binary @input.jpg http://localhost:9000/upload -o output.jpg
```
The image has been successfully compressed and does not contain EXIF!


## Build setup
### Jenkins

To set up continuous integration with Jenkins, use the `Jenkinsfile` located in the root of the project. Jenkins will
automatically detect and use this file when you configure your Jenkins job with the "Pipeline from SCM" option. This
will trigger the pipeline to build the project and create the `image-compression-server-1.0-all.jar` file

### GitHub Actions

To set up continuous integration with GitHub Actions, use the `.github/workflows/gradle.yml` file located in the root of
the repository. This file defines the steps required to build the project and create the `image-compression-server-1.0-all.jar` file

GitHub Actions will automatically detect and use this workflow when you push code to the repository. It will trigger the
defined steps, including setting up the environment, installing dependencies, and running the Gradle build tasks to
create the `image-compression-server-1.0-all.jar` file

### Manual

To manually build the project, follow these steps:

```bash
# clone the repository
$ git clone https://github.com/hermippus/image-compression-server

# navigate to the project directory
$ cd image-compression-server

# build with Gradle (Fat Jar)
$ ./gradlew shadowJar
```

The jar file is located in `build/libs/`


**TO-DO:**
- [ ] Movint to Spring
- [ ] PNG support
- [ ] Another file upload method
- [ ] Docker deployment
