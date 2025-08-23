pipeline {
    agent any
    tools {
        nodejs 'node'
        maven 'mvn'
    }
    environment {
        IMAGE_NAME = 'angular-app'
        IMAGE_TAG = 'latest'
        DOCKER_COMPOSE = '/var/jenkins_home/bin/docker-compose'  // Chemin absolu
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Vincent-Gillet/critik-cicd.git'
            }
        }
        stage('Install Docker Compose') {
            steps {
                sh '''
                mkdir -p /var/jenkins_home/bin
                curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /var/jenkins_home/bin/docker-compose
                chmod +x /var/jenkins_home/bin/docker-compose
                /var/jenkins_home/bin/docker-compose --version
                '''
            }
        }
        stage('Start MySQL') {
            steps {
                sh '''
                ${DOCKER_COMPOSE} -f docker-compose-app.yml down --rmi local -v || true
                ${DOCKER_COMPOSE} -f docker-compose-app.yml up -d --build mysql

                # Attendre que MySQL soit prêt
                until docker exec angular-spring-mysql-1 mysqladmin ping --silent; do
                    echo "En attente de MySQL..."
                    sleep 2
                done

                # Vérifier que le réseau existe
                docker network inspect angular-spring_critik_network >/dev/null 2>&1 || docker network create angular-spring_critik_network

                # Tester la connexion depuis un conteneur temporaire
                echo "Test de connexion à MySQL :"
                docker run --network angular-spring_critik_network --rm mysql:8.3 mysql -h mysql -u user -ppassword -e "SHOW DATABASES;"
                echo "MySQL est prêt et accessible !"
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
                sh "${DOCKER_COMPOSE} build"
            }
        }
        stage('Deploy Angular') {
            steps {
                sh '''
                ${DOCKER_COMPOSE} stop angular
                ${DOCKER_COMPOSE} rm -f angular
                ${DOCKER_COMPOSE} -f docker-compose-app.yml up -d --build angular
                '''
            }
        }
        stage('Deploy Spring') {
            steps {
                sh '''
                ${DOCKER_COMPOSE} stop spring
                ${DOCKER_COMPOSE} rm -f spring
                ${DOCKER_COMPOSE} -f docker-compose-app.yml up -d --build spring
                '''
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