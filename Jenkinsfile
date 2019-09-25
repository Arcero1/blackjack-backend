pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package'
      }
    }
    stage('a docker test stage') {
      steps {
        sh 'pwd'
        sh 'sudo docker-compose build'
        sh 'sudo docker-compose up -d'
      }
    }
    stage('') {
      steps {
        sh 'mvn test -Dtest=!com.qa.blackjack.selenium*'
      }
    }
  }
}