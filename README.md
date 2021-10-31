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
4. Visit localhost:8080 for documentation and examples

Please refer to the Quarkus documentation for other options (native executable, fat-jar, ...).

## Links
* [ZXing](https://github.com/zxing/zxing)
* [Quarkus](https://quarkus.io/)
* [Maven](https://maven.apache.org/)

## License
Copyright 2021 Yannick Häßler

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.