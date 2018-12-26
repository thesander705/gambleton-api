pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    options {
        timeout(time: 5, unit: 'MINUTES')
    }
    stages {
        stage('Unit and integration tests') {
            steps {
                sh ' mvn jacoco:prepare-agent test failsafe:integration-test jacoco:report'
            }
        }
        stage('Code style check') {
            steps{
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
            }
        }
        stage('Installing') {
            steps{
                sh 'mvn install'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
