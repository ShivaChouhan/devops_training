
#for jenkins ans github actions
FROM openjdk:17-jdk-slim


#for aws code_pipeline 
# FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
VOLUME /tmp
COPY target/hello-springboot-app-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]