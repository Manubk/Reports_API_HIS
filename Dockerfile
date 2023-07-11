FROM khipu/openjdk17-alpine
MAINTAINER <maltesh>
COPY ./target/*.jar RP_Api.jar
ENTRYPOINT [ "java","-jar","RP_Api.jar" ]