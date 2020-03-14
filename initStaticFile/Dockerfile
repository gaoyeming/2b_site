FROM java:8
VOLUME /tmp
ADD 2B-site.jar 2B-site.jar
RUN bash -c 'touch /2B-site.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/2B-site.jar"]