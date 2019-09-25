pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh 'mvn -Dmaven.test.skip=true package'
        sh 'nohup java -jar target/blackjack-extended-0.0.1-SNAPSHOT.jar &'
      }
    }
  }
}