pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                echo 'checking out the application'
                deleteDir()
                checkout scm
            }
        }
        stage('Build stage') {
            steps {
                withMaven(maven : 'maven_3.1.0'){
                    bat 'mvn clean install'
                }
            }
        }
        stage('Deploy stage') {
            steps {
               script{
                    bat "copy %".\Customer-service\target\*.war%" %"C:\tomcat\apache-tomcat-9.0.64\webapps%""
               }
            }
        }
    }
}
