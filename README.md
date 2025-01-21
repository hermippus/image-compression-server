# Image Compression Server
Spring Web Server with image compression (JPG only) and EXIF cleaner.

Compression is achieved by using standard Java library - `javax.imageio`

## Tech stack:
* Java 21
* Spring
* Gradle

## Requirements
* Java 21

## Usage
```
# Start the Spring Web Server using the command:
$ java -jar image-compression-server-2.0.0.jar

# Using cURL to transfer image
$ curl -X POST -F "file=@input.jpg" http://localhost:9000/upload --output output.jpg
```
The image has been successfully compressed and does not contain EXIF!


## Build setup
### Jenkins

To set up continuous integration with Jenkins, use the `Jenkinsfile` located in the root of the project. Jenkins will
automatically detect and use this file when you configure your Jenkins job with the "Pipeline from SCM" option. This
will trigger the pipeline to build the project and create the `image-compression-server-2.0.0.jar` file

### GitHub Actions

To set up continuous integration with GitHub Actions, use the `.github/workflows/gradle.yml` file located in the root of
the repository. This file defines the steps required to build the project and create the `image-compression-server-2.0.0.jar` file

GitHub Actions will automatically detect and use this workflow when you push code to the repository. It will trigger the
defined steps, including setting up the environment, installing dependencies, and running the Gradle build tasks to
create the `image-compression-server-2.0.0.jar` file

### Manual

To manually build the project, follow these steps:

```bash
# clone the repository
$ git clone https://github.com/hermippus/image-compression-server

# navigate to the project directory
$ cd image-compression-server

# build with Gradle (Jar)
$ ./gradlew build
```

The jar file is located in `build/libs/`

**TO-DO:**
- [x] Move to Spring Framework (Done in v2.0.0)
- [ ] PNG support
- [x] Another file upload method (Done in v2.0.0)
- [ ] Docker deployment
