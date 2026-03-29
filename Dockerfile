#FROM gradle:8.14.2-jdk-21 AS build
#FROM gradle:8.5-jdk21 AS build
#COPY --chown=gradle.gradle . /app
#WORKDIR app
#RUN gradle bootJar --no-daemon


FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle bootJar --no-daemon -Dorg.gradle.jvmargs="-Xmx256m -XX:MaxMetaspaceSize=128m"


FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar play.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "play.jar"]