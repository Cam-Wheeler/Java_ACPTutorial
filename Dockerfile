FROM openjdk:23
LABEL authors="cameronwheeler"
EXPOSE 8080

ENV BLOB_STORAGE_ENDPOINT=BlobEndpoint=https://acpstoragedemo.blob.core.windows.net/;QueueEndpoint=https://acpstoragedemo.queue.core.windows.net/;FileEndpoint=https://acpstoragedemo.file.core.windows.net/;TableEndpoint=https://acpstoragedemo.table.core.windows.net/;SharedAccessSignature=sv=2022-11-02&ss=b&srt=sco&sp=rwdlaciytfx&se=2026-01-13T06:44:08Z&st=2025-01-12T22:44:08Z&spr=https&sig=4pTytT2m2e%2BJRuao9azJ5IFBfJGRAhLHVCsUTSEtx7E%3D
ENV BLOB_CONTAINER_NAME=acpdemocontainer

WORKDIR /app
COPY /target/acpdemo*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]