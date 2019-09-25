pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package && java -jar target/gs-spring-boot-0.1.0.jar'
      }
    }
  }
}