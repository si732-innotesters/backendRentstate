pipeline {
    agent any
    tools {
        maven 'maven_3_9_9' // Usa el nombre correcto configurado en Jenkins
        jdk 'jdk-21' // Asegúrate que también coincida el nombre del JDK configurado
    }

    stages {
        stage ('Compile Stage 2023-02') {
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

        stage ('Testing Stage 2023-02') {
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

        // Puedes descomentar este bloque si configuras SonarQube en el futuro.
        /*stage ('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarLocal') {
                    script {
                        if (isUnix()) {
                            sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=one'
                        } else {
                            bat 'mvn clean verify sonar:sonar -Dsonar.projectKey=one'
                        }
                    }
                }
            }
        }*/

        stage ('Package Stage 2023-2') {
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

        // Descomentar cuando se tenga instalado en Tomcat
        /*stage('Deploy to Tomcat') {
            steps {
                echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} at ${env.WORKSPACE}"
                withMaven(maven: 'maven_3_9_9') {
                    script {
                        if (isUnix()) {
                            sh 'curl -T ./target/sistema-ventas-spring.war "http://tomcat:tomcat@localhost:9090/manager/text/deploy?path=/sistema-ventas-spring&update=true"'
                        } else {
                            bat '"C:\\Program Files\\Git\\mingw64\\bin\\curl.exe" -T ".\\target\\sistema-ventas-spring.war" "http://tomcat:tomcat@localhost:9090/manager/text/deploy?path=/sistema-ventas-spring&update=true"'
                        }
                    }
                }
            }
        }*/
    }
}
