docker build -t wso2.uranium/ui:v1.0.0 .
docker run --network host  -p 8080:8080 -t wso2.uranium/ui:v1.0.0