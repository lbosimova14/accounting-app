FROM amd64/maven:3.8.6-openjdk-11
WORKDIR usr/app
# 2 dot means - taking all folder and puting inside current directory which is usr/app
COPY  .  .
ENTRYPOINT ["mvn","spring-boot:run"]