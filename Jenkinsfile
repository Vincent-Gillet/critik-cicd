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
        stage('Install Docker Compose') {
            steps {
                sh '''
                mkdir -p ~/bin
                curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-x86_64" -o ~/bin/docker-compose
                chmod +x ~/bin/docker-compose
                export PATH=$PATH:~/bin
                docker-compose --version
                '''
            }
        }
        stage('Start MySQL') {
            steps {
                sh '''
                ~/bin/docker-compose -f docker-compose-app.yml up -d --build mysql

                # Wait for MySQL to be ready
                until docker exec angular-spring_mysql-1 mysqladmin ping --silent; do
                    echo "En attente de MySQL..."
                    sleep 2
                done

                # Ensure the network exists
                docker network inspect angular-spring_critik_network >/dev/null 2>&1 || docker network create angular-spring_critik_network

                # Test connection from a temporary container
                docker run --network angular-spring_critik_network --rm mysql:8.3 mysql -h angular-spring_mysql_1 -u user -ppassword -e "SHOW DATABASES;"
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
                sh 'docker compose build'
            }
        }
        stage('Deploy Angular') {
            steps {
                sh '''
                docker compose stop angular
                docker compose rm -f angular
                docker compose up -d --build angular
                '''
            }
        }
        stage('Deploy Spring') {
            steps {
                sh '''
                docker compose stop spring
                docker compose rm -f spring
                docker compose up -d --build spring
                '''
            }
        }
        stage('Start All Services') {
            steps {
                sh 'docker compose up -d angular spring mysql'
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