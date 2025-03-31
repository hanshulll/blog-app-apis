FROM openjdk:25-slim-bullseye
LABEL authors="hanshulchandel"
COPY blogsphere.jar blogsphere.jar
ENTRYPOINT ["java","-jar","/blogsphere.jar"]
