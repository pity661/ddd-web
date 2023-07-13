# docker pull --platform linux/x86_64 ${images}:${version}
# ./elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.17.0/elasticsearch-analysis-ik-7.17.0.zip

# docker cp ./xx ${container_id}:/
# unzip elasticsearch-analysis-ik-7.17.0.zip -d plugins/ik
# docker restart
docker-compose -f docker-compose.yaml up -d --build