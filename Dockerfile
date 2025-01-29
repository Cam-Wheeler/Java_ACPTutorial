FROM openjdk:21

LABEL authors="cameronwheeler"

EXPOSE 8080

ENV BLOB_STORAGE_ENDPOINT="BlobEndpoint=https://acpstoragedemo.blob.core.windows.net/;QueueEndpoint=https://acpstoragedemo.queue.core.windows.net/;FileEndpoint=https://acpstoragedemo.file.core.windows.net/;TableEndpoint=https://acpstoragedemo.table.core.windows.net/;SharedAccessSignature=sv=2022-11-02&ss=b&srt=co&sp=rwdlytfx&se=2026-01-29T18:04:36Z&st=2025-01-29T10:04:36Z&spr=https&sig=66iJOwwZe4yxhMF9QAurVrKEAKz9OUDjiFvpFZjLpjM%3D"
ENV BLOB_CONTAINER_NAME="acpdemocontainer"

WORKDIR /app

COPY /target/acpdemo*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
