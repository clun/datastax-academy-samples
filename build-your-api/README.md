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


Download the required images from [Dockerhub](https://hub.docker.com/u/datastax)
```
docker pull datastax/dse-server
```

