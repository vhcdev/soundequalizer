# original documentation: https://github.com/docker-library/docs/tree/master/mysql

#build docker dabatase
docker build -t vhcsoft/mysql5.6 .

#Run your images with a name
docker rm mveodb
docker run -d --name mveodb mveo/mysql5.6

#stop and remove all containers

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

#remove all untagged images
docker rmi $(docker images | grep "^<none>" | awk "{print $3}")

#connect to container by name
sudo docker exec -i -t mveodb bash 

###########PUSH IMAGES TO GOOGLE###############################################

#push the images on google cloud 
#rename the docker images 

docker tag [images id] gcr.io/[google computer engine id]/[images name]

#example 
docker tag e4323dded gcr.io/mveo-1021/mveodb

#push docker images to googe cloud. Example
gcloud docker push gcr.io/mveo-1021/mveodb