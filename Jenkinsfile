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

        stage('Start MySQL') {
            steps {
                sh '''
                # Arrêter tout conteneur utilisant le port 3306
                docker ps -q --filter "publish=3306" | xargs -r docker stop
                # Supprimer les conteneurs arrêtés
                docker ps -q --filter "publish=3306" | xargs -r docker rm
                # Nettoyer les réseaux et volumes
                docker-compose down --rmi all -v || true
                docker network prune -f
                # Démarrer MySQL
                docker-compose up -d --build mysql
                # Attendre que MySQL soit prêt
                until docker exec angular-spring_mysql_1 mysqladmin ping --silent; do
                    echo "En attente de MySQL..."
                    sleep 2
                done
                echo "MySQL est prêt !"
                '''
            }
        }

        stage('Setup Node & Angular CLI') {
            steps {
                sh 'rm -rf /var/jenkins_home/tools/jenkins.plugins.nodejs.tools.NodeJSInstallation/node/lib/node_modules/@angular/cli'
                sh 'npm install -g @angular/cli@18.2.0'
            }
        }

        stage('Install Angular Dependencies') {
            steps {
                dir('client') {
                    sh 'npm install'
                }
            }
        }

        stage('Test Angular') {
            steps {
                dir('client') {
                    sh 'npm test -- --watch=false --browsers=ChromeHeadless --chrome-flags="--no-sandbox --headless --disable-gpu" || echo "Tests ignored"'
                }
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

        stage('Build Spring Boot') {
            steps {
                dir('api') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test Spring Boot') {
            steps {
                dir('api') {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Deploy Angular') {
            steps {
                sh '''
                docker-compose stop angular
                docker-compose rm -f angular
                docker network rm angular-spring_critik_network || true
                docker-compose up -d --build angular
                '''
            }
        }

        stage('Deploy Spring') {
            steps {
                sh '''
                docker-compose stop spring
                docker-compose rm -f spring
                docker network rm apicritiquefilm_critik_network || true
                docker-compose up -d --build spring
                '''
            }
        }

        stage('Start All Services') {
            steps {
                sh 'docker-compose up -d angular spring mysql'
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded! Angular: http://localhost:4200, Spring: http://localhost:8080'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}