pipeline {
  agent any
  stages {
    stage('a test stage') {
      steps {
        sh '''def mvn_version = \'MVN\'
withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
  //sh "mvn clean package"
}'''
      }
    }
  }
}