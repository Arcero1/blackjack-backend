pipeline {
  agent {
    dockerfile {
      filename 'Dockerfile'
    }

  }
  stages {
    stage('a test stage') {
      steps {
        sh 'docker run -dit -p 3000:80 '
      }
    }
  }
}