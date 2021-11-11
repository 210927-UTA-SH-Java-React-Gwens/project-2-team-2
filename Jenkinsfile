pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "mvn clean install"
                sh "mvn spring-boot:run"
            }
        }
    }
}
