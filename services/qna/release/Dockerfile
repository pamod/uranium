FROM eclipse-temurin:11
ARG USERNAME=uranium
ARG USER_UID=10014
ARG USER_GID=$USER_UID

# Create a new user with UID 10014
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

RUN mkdir -p /workspace/app && chown -R $USERNAME /workspace/app && cd /workspace/app
WORKDIR /workspace/app
VOLUME /tmp
USER 10014
COPY Uranium-1.0-SNAPSHOT.jar .
COPY config config
COPY start.sh .
ENTRYPOINT ["sh","start.sh"]

ENV HOST '0.0.0.0'
EXPOSE 8080
