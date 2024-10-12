FROM node:18-buster
ARG USERNAME=uranium
ARG USER_UID=10014
ARG USER_GID=$USER_UID

# Create a new user with UID 10014
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

RUN mkdir -p /workspace/app && chown -R $USERNAME /workspace/app && cd /workspace/app
WORKDIR /workspace/app

COPY public/ ./public
COPY src/ ./src
COPY package.json .
COPY .env .

RUN npm install

EXPOSE 3000

ENTRYPOINT ["npm","start"]
