pipeline {
    agent any
    tools {
        maven 'maven_3_9_9'
        jdk 'jdk-21'
    }

    stages {
        stage ('Compile Stage 2024-02') {
            steps {
                withMaven(maven: 'maven_3_9_9') {
                    script {
                        if (isUnix()) {
                            sh 'mvn clean compile'
                        } else {
                            bat 'mvn clean compile'
                        }
                    }
                }
            }
        }

        stage ('Testing Stage 2024-02') {
            steps {
                withMaven(maven: 'maven_3_9_9') {
                    script {
                        if (isUnix()) {
                            sh 'mvn test'
                        } else {
                            bat 'mvn test'
                        }
                    }
                }
            }
        }



        stage ('Package Stage 2024-2') {
            steps {
                withMaven(maven: 'maven_3_9_9') {
                    script {
                        if (isUnix()) {
                            sh 'mvn package'
                        } else {
                            bat 'mvn package'
                        }
                    }
                }
            }
        }


    }
}
