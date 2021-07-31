# Super*Duper*Drive Cloud Storage

[![Build Status](https://travis-ci.com/fespinosa-dev/cloudstorage.svg?branch=master)](https://travis-ci.com/fespinosa-dev/cloudstorage)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d0ca7f15e33e42d4ac9ba56e203139b4)](https://www.codacy.com/gh/fespinosa-dev/cloudstorage/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fespinosa-dev/cloudstorage&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/d0ca7f15e33e42d4ac9ba56e203139b4)](https://www.codacy.com/gh/fespinosa-dev/cloudstorage/dashboard?utm_source=github.com&utm_medium=referral&utm_content=fespinosa-dev/cloudstorage&utm_campaign=Badge_Coverage)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B6?s&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/fjevictoriano/)

Super*Duper*Drive is a simple cloud storage web application that lets you manage and store files, notes and passwords.

## Features

1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  


## Technologies used

* The back-end with [Spring Boot](https://spring.io/projects/spring-boot)
* The front-end with [Thymeleaf](https://www.thymeleaf.org/)
* Application tests with [Selenium](https://www.selenium.dev/)


## Requirements

For building and running the application you need:

- [JDK 1.8+](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `dev.fespinosa.cloudstorage.CloudStorageApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Running the tests

You can run the tests wht maven like so:

```shell
mvn test 
```

## Deploying application to AWS Elastic Beanstalk
In progress...

<!-- LICENSE -->
## License

Distributed under the  [MIT](https://github.com/fespinosa-dev/ckd-tracker/blob/master/LICENSE) License.