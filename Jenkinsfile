pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package'
      }
    }
    stage('a docker test stage') {
      steps {
        sh 'docker-compose build'
        sh 'docker-compose up -d'
      }
    }
  }
}