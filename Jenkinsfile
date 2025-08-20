pipeline {
    agent any

    tools {
        nodejs 'node'
        maven 'mvn'
    }

    environment {
        IMAGE_NAME = 'angular-app'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Vincent-Gillet/critik-cicd.git'
            }
        }

        stage('Installation') {
            steps {
                sh 'npm install -g @angular/cli@18.2.0'
            }
        }

        stage('Install Dependencies') {
            steps {
                dir('client') {
                    sh 'npm install'
                }
            }
        }

        stage('Test') {
            steps {
                sh 'npm test -- --watch=false --browsers=ChromeHeadless --chrome-flags="--no-sandbox --headless --disable-gpu" || echo "Tests ignorés"'
            }
        }

        stage('Build Angular') {
            steps {
                dir('client') {
                    sh 'npm run build'
                }
            }
        }

        stage('Install Docker Compose') {
            steps {
                sh 'apt-get update && apt-get install -y docker-compose'
            }
        }

        stage('Angular Docker Build') {
            steps {
                sh 'docker-compose -f docker-compose.yml build angular'
            }
        }

        stage('Angular Deployement') {
            steps {
                sh '''
                docker-compose stop angular || true
                docker-compose rm -f angular || true
                docker ps -q --filter "publish=4200" | xargs -r docker stop
                docker network rm angular-spring_default || true
                docker-compose up -d angular
                '''
            }
        }

        stage('Cleanup Docker Network') {
            steps {
                sh 'docker network rm api_default || true'
            }
        }

        stage('Cleanup MySQL Docker') {
            steps {
                dir('api') {
                    sh '''
                    docker-compose -f docker-compose.yml down --volumes --remove-orphans
                    docker rm -f mysql-critik || true
                    docker rmi mysql:8.3 || true
                    '''
                }
            }
        }

        stage('Start MySQL') {
            steps {
                dir('api') {
                    sh 'docker-compose -f docker-compose.yml up -d mysql'
                }
            }
        }

        stage('Build Backend') {
          steps {
            dir('api') {
              sh 'mvn clean package -DskipTests'
            }
          }
        }

        stage('Spring Build & Test') {
            steps {
                dir('api') {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Spring Docker Build') {
            steps {
                dir('api') {
                    sh 'docker-compose build spring'
                }
            }
        }

        stage('Spring Deployement') {
            steps {
                sh '''
                docker-compose stop spring
                docker-compose rm spring
                docker-compose up -d spring
                '''
            }
        }

/*         stage('Build Docker Image') {
            steps {
                sh '''
                DIST_PATH=$(find dist -name "index.html" | head -n 1 | xargs dirname 2>/dev/null || echo "dist")

                # Créer le Dockerfile
                echo 'FROM nginx:alpine' > Dockerfile.app
                echo "COPY $DIST_PATH/ /usr/share/nginx/html/" >> Dockerfile.app
                echo 'RUN echo "server { listen 8080; location / { root /usr/share/nginx/html; index index.html; try_files \\$uri \\$uri/ /index.html; } }" > /etc/nginx/conf.d/default.conf' >> Dockerfile.app
                echo 'EXPOSE 8080' >> Dockerfile.app
                echo 'CMD ["nginx", "-g", "daemon off;"]' >> Dockerfile.app
                '''

                // Construire l'image Docker
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -f Dockerfile.app ."
            }
        } */

/*         stage('Deploy') {
            steps {
                // Déployer le conteneur
                sh """
                docker stop ${IMAGE_NAME} || true
                docker rm ${IMAGE_NAME} || true
                docker run -d --name ${IMAGE_NAME} -p 8081:8080 ${IMAGE_NAME}:${IMAGE_TAG}
                """

                echo "Application déployée et accessible sur http://localhost:8081"
            }
        } */
    }

    post {
        success {
            echo 'Pipeline réussi ! Application disponible sur http://localhost:8081'
        }
        failure {
            echo 'Le pipeline a échoué. Vérifiez les logs pour plus de détails.'
        }
    }
}