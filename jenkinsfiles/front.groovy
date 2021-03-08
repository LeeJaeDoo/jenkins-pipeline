pipeline {
    agent any

    tools {nodejs "node"}

    stages {

        stage('Git') {
            steps {
                git 'https://github.com/LeeJaeDoo/sp-fe.git'
            }
        }

        stage('Build') {
            steps {
                sh 'npm install'
                sh '<<Build Command>>'
            }
        }


        stage('Test') {
            steps {
                sh 'node test'
            }
        }
    }
}