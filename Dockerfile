FROM openjdk:23
LABEL authors="cameronwheeler"
EXPOSE 8080

ENV BLOB_STORAGE_ENDPOINT="BlobEndpoint=https://acpdemoservice.blob.core.windows.net/;QueueEndpoint=https://acpdemoservice.queue.core.windows.net/;FileEndpoint=https://acpdemoservice.file.core.windows.net/;TableEndpoint=https://acpdemoservice.table.core.windows.net/;SharedAccessSignature=sv=2022-11-02&ss=b&srt=c&sp=rwdlitfx&se=2026-01-13T02:11:14Z&st=2025-01-12T18:11:14Z&spr=https&sig=fkbf6GwhKusDhz9X%2FGp0kFTqON%2B62HrHB3pvFQ0xtko%3D"
ENV BLOB_CONTAINER_NAME="acpdemocontainer"

WORKDIR /app
COPY /target/acpdemo*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]