# Building yout API on top of DSE

URL of the course

### 1. Setup your environment
Follow this instruction to setup your environment 

* Install Java and Maven
```
brew tap caskroom/versions
brew update
brew cask install java8
brew install maven
```

* Download and install your favorite IDE : [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/), [Eclipse STS](https://spring.io/tools/sts/all)
or [IntelliJ](https://www.jetbrains.com/idea/)

* Download and install Docker
```
brew install docker
```


* Download the required images from [Dockerhub](https://hub.docker.com/u/datastax)

For second image, you notice that some layers have already been downloaded which may speed up
download.
```
docker pull datastax/dse-server
docker pull datastax/dse-studio
docker images | grep datastax
```

* Start Server
To run the DSE we need to accept the license. Do do it simply simply add environment variable using docker syntax in the following way. 
```
docker run -e "DS_LICENSE=accept" -it -d -p 9042:9042 --name dse6 datastax/dse-server
```

* Start Studio
```
docker run -e "DS_LICENSE=accept" -it -d -p 9091:9091 --link dse6:dse6 datastax/dse-studio
```

* Connect to [Datastax Studio](http://localhost:9091)
```
- Edit connection to use hostname dse6 (and not 127.0.0.1)
- Create a notebook "REST API"
```

* Create KeySpace
```sql
CREATE KEYSPACE IF NOT EXISTS demo WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
```

* Create Schema
```sql
// Comments for a given video
CREATE TABLE IF NOT EXISTS demo.comments_by_video (
    videoid uuid,
    commentid timeuuid,
    userid uuid,
    comment text,
    PRIMARY KEY (videoid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

// Comments for a given user
CREATE TABLE IF NOT EXISTS demo.comments_by_user (
    userid uuid,
    commentid timeuuid,
    videoid uuid,
    comment text,
    PRIMARY KEY (userid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

```


