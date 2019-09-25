pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh '''apt-get install maven
mvn package && java -jar target/gs-spring-boot-0.1.0.jar'''
      }
    }
  }
}