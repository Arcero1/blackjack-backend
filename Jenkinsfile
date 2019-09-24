pipeline {
  agent {
    dockerfile {
      filename 'Dockerfile'
    }

  }
  stages {
    stage('a test stage') {
      steps {
        sh 'mvn spring-boot:run'
      }
    }
  }
}