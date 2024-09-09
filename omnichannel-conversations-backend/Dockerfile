FROM tomee:10.0.0-M2-jre17-Temurin-ubuntu-plus
RUN rm -r /usr/local/tomee/webapps/ROOT
RUN echo "tomee.mp.scan = all" >> conf/system.properties
COPY build/libs/omnichannel-conversations-backend.war /usr/local/tomee/webapps/ROOT.war
