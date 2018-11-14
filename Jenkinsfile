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
                      '-Dsonar.exclusions=**/*Test*/**' +
                      '-Dsonar.language=java' +
                      '-Dsonar.tests=junit' +
                      '-Dsonar.binaries=build/classes' +
                      '-Dsonar.dynamicAnalysis=reuseReports' +
                      '-Dsonar.junit.reportsPath=build/test-reports' +
                      '-Dsonar.java.coveragePlugin=jacoco' +
                      '-Dsonar.jacoco.reportPath=build/test-reports/jacoco.exec'
            }
        }
    }
}
