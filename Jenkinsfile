pipeline {
  agent any
  stages {
    stage('build jar') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package'
      }
    }
    stage('test') {
      steps {
        sh 'mvn surefire-report:report -DoutputDirectory=~ -Dtest=!com.qa.blackjack.selenium*'
      }
    }
    stage('build/deploy docker') {
      steps {
        sh 'sudo docker-compose build'
        sh 'sudo docker-compose up -d'
      }
    }
  }
}