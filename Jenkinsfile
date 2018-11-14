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
        stage('sonarqube') {
            steps{
                      sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar ' +
                      '-Dsonar.test.inclusions=**/*Test*/** ' +
                      '-Dsonar.exclusions=**/*Test*/**'
            }
        }
    }
}
