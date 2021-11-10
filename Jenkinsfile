pipeline {
     agent any
     options {
        skipDefaultCheckout(true)
    }
     stages {
        stage("Build") {
            steps {
                sh "cleanWs()"
                sh "checkout scm" 
                sh "mvn clean install"
                sh "mvn spring-boot:run"
            }
        }
    }
}
