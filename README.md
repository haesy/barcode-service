# Barcode Service
REST API to generate barcodes based on ZXing and Quarkus.

## Supported Formats
* Code 128
* EAN-8
* EAN-13
* ITF
* QR-Code

## Requirements
* Java 17
* Maven

## Usage
1. Build with Maven: ```mvn clean package```
2. The directory target/quarkus-app contains the whole application with all dependencies
3. Execute ```java -jar quarkus-run.jar``` to start the server
4. Visit localhost:8080

Please refer to the Quarkus documentation for other options (native executable, fat-jar, ...).

## Links
* [ZXing](https://github.com/zxing/zxing)
* [Quarkus](https://quarkus.io/)
* [Maven](https://maven.apache.org/)
