pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package'
        sh 'pwd'
      }
    }
    stage('a docker test stage') {
      steps {
        sh 'pwd'
        sh 'sudo docker-compose build'
        sh 'sudo docker-compose up'
      }
    }
  }
}