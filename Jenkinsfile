pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    options {
        timeout(time: 5, unit: 'MINUTES')
    }
    stages {
        stage('Installing') {
            steps {
               sh 'mvn install'
            }
        }
    }
}
