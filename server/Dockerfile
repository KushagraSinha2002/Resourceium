#### Stage 1: Build the application
FROM openjdk:15-alpine

# Set the maven version and add it to $PATH
ARG MAVEN_VERSION=3.6.1
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

# Install maven
RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# Set the current working directory inside the image
WORKDIR /app
RUN mvn dependency:go-offline -B --fail-never

# Run the development command
ENTRYPOINT [ "mvn", "spring-boot:run" ]
