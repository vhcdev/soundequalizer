#build docker images:

$ mvn package docker:build

#Run docker database 
docker run -d --name mveodb mveo/mysql5.6

#Run your images with a name
docker run -d --name mveoweb -p 8080:8080 --link mveodb:mveodb vhcsoft/sound.equalizer   

#Run interactive mode
docker run --name mveoweb -p 80:8080 --link mveodb:mveodb -it vhcsoft/sound.equalizer /bin/bash
#run "java -jar app.jar" in docker container   

#connect to container by name
sudo docker exec -i -t mveo bash 