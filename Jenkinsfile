pipeline {
    agent any
    tools {
        nodejs 'node'
        maven 'mvn'
    }
    environment {
        IMAGE_NAME = 'angular-app'
        IMAGE_TAG = 'latest'
        DOCKER_COMPOSE = '/var/jenkins_home/bin/docker-compose'
        SONAR_PROJECT_KEY = 'Vincent-Gillet_critik-cicd'
        SONAR_ORGANIZATION = 'critik-sonar'
        SONAR_HOST_URL = 'https://sonarcloud.io'

        SONAR_LOGIN = credentials('sonar-token')

/*
        SONAR_LOGIN = '0648ab4d42c63de71c83f971849770c94cd9fb65'
 */
        DOCKER_REGISTRY = 'vincentgillet12'
        DOCKER_REGISTRY_IMAGE = 'docker.io/vincentgillet12'
        DOCKER_REGISTRY_CREDENTIALS = credentials('docker-hub-credentials')


        RENDER_API_TOKEN = 'rnd_z9zHYwJSBX9VmXrAiLNedzkdNls6'
        RENDER_SERVICE_ID_ANGULAR = 'srv-d36ubuh5pdvs739ldp00'
        RENDER_SERVICE_ID_SPRING = 'srv-d36udqbe5dus738s4a1g'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Vincent-Gillet/critik-cicd.git'
            }
        }
        stage('Build Spring Boot') {
            steps {
                dir('api') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('api') {
                    sh '''
                        mvn sonar:sonar \
                          -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                          -Dsonar.organization=${SONAR_ORGANIZATION} \
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.login=${SONAR_LOGIN}
                    '''
                }
            }
        }
        stage('Docker Login') {
            steps {
                sh 'echo $DOCKER_REGISTRY_CREDENTIALS_PSW | docker login docker.io -u $DOCKER_REGISTRY_CREDENTIALS_USR --password-stdin'
            }
        }
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def services = [
                        [name: 'spring-app', path: './api', dockerfile: 'production.Dockerfile'],
                        [name: 'angular-app', path: './client', dockerfile: 'production.Dockerfile']
                    ]
                    services.each { svc ->
                        sh """
                            docker build --platform=linux/amd64 -t $DOCKER_REGISTRY/critik-${svc.name}:$BUILD_NUMBER -t $DOCKER_REGISTRY/critik-${svc.name}:latest -f ${svc.path}/${svc.dockerfile} ${svc.path}
                            docker push $DOCKER_REGISTRY/critik-${svc.name}:$BUILD_NUMBER
                            docker push $DOCKER_REGISTRY/critik-${svc.name}:latest
                        """
                    }
                }
            }
        }
        stage('Deploy to Render') {
            steps {
                script {
                    def renderServices = [
                        [name: 'angular-app', id: env.RENDER_SERVICE_ID_ANGULAR],
                        [name: 'spring-app', id: env.RENDER_SERVICE_ID_SPRING]
                    ]
                    renderServices.each { svc ->
                        sh """
                            curl --request POST \
                            --url "https://api.render.com/v1/services/${svc.id}/deploys" \
                            --header 'accept: application/json' \
                            --header "authorization: Bearer $RENDER_API_TOKEN" \
                            --header 'content-type: application/json' \
                            --data '{\"clearCache\": \"clear\", \"imageUrl\": \"$DOCKER_REGISTRY_IMAGE/critik-${svc.name}:latest\"}'
                        """
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline succeeded! Angular: https://critik-angular-app-4eyi.onrender.com/, Spring: https://critik-spring-app-ter3.onrender.com'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
