FROM gradle:7.6.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --scan

FROM openjdk:21-slim
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/sadaparcel-0.0.1-SNAPSHOT-plain.jar /app/
RUN bash -c 'touch /app/sadaparcel-0.0.1-SNAPSHOT-plain.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/sadaparcel-0.0.1-SNAPSHOT-plain.jar"]