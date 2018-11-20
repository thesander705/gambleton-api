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
                sh 'mvn jacoco:prepare-agent install jacoco:report'
            }
        }
        stage('sonarqube') {
            steps{
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
            }
        }
    }
}
