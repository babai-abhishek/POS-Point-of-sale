pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                echo 'checking out the application'
                deleteDir()
                checkout scm
            }
//         }
//         stage('Run docker compose file'){
//             steps{
//                 script{
//                     bat "docker-compose -f pos-docker-compose.yml up -d"
//                 }
//             }
//         }
        stage('Build stage') {
            steps {
                withMaven(maven : 'maven_3.1.0'){
                    bat 'mvn clean install'
                }
            }
        }
    }
}
