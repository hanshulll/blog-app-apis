FROM openjdk:25-slim-bullseye
LABEL authors="hanshulchandel"
ADD target/blogsphere.jar blogsphere.jar
ENTRYPOINT ["java","-jar","/blogsphere.jar"]
