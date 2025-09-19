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
        DOCKER_REGISTRY_CREDENTIALS = credentials('docker-hub-credentials')


/*         RENDER_API_TOKEN = credentials('render-api-token')
        RENDER_SERVICE_ID_ANGULAR = 'your-angular-service-id'
        RENDER_SERVICE_ID_SPRING = 'your-spring-service-id' */
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
                curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o ${DOCKER_COMPOSE}
                chmod +x ${DOCKER_COMPOSE}
                ${DOCKER_COMPOSE} --version
                '''
            }
        }
        stage('Start MySQL') {
            steps {
                sh '''
                ${DOCKER_COMPOSE} -f docker-compose-app.yml down --rmi local -v || true
                ${DOCKER_COMPOSE} -f docker-compose-app.yml up -d --build mysql
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
        stage('Start Angular for Selenium') {
            steps {
                dir('client') {
                    sh 'nohup npm start &'
                    // Wait for Angular to be ready (e.g., sleep 20)
                    sh 'sleep 20'
                }
            }
        }
/*         stage('Test with Selenium Standalone') {
            steps {
                sh '''
                docker run -d --name selenium -p 4444:4444 --platform=linux/arm64 seleniarm/standalone-chromium:latest
                # Run your Selenium tests pointing to http://localhost:4444/wd/hub
                '''
            }
        }
        stage('Setup Chrome for Selenium') {
            steps {
                sh '''
                apt-get update
                apt-get install -y wget unzip libgbm-dev libnss3 libx11-xcb1 libxcomposite1 libxcursor1 libxdamage1 libxi6 libxtst6 libxrandr2 libasound2 libatk1.0-0 libatk-bridge2.0-0 libgtk-3-0
                wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
                apt-get install -y ./google-chrome-stable_current_amd64.deb
                wget https://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip
                unzip chromedriver_linux64.zip
                mv chromedriver /usr/bin/chromedriver
                chmod +x /usr/bin/chromedriver
                '''
            }
        } */
/*         stage('Test Spring Boot') {
            steps {
                dir('api') {
 *//*
                    sh 'mvn clean compile'
                    sh 'mvn test -Dmaven.test.failure.ignore=true'
*//*

                    sh 'mvn clean install'

                }
            }
        } */
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
                        [name: 'mysql-app', path: './database', dockerfile: 'Dockerfile'],
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

        stage('Deploy MySQL to Render') {
            steps {
                sh '''
                    curl --request POST \\
                    --url "https://api.render.com/v1/services/$RENDER_SERVICE_ID_MYSQL/deploys" \\
                    --header 'accept: application/json' \\
                    --header "authorization: Bearer $RENDER_API_TOKEN" \\
                    --header 'content-type: application/json' \\
                    --data '{"clearCache": "clear", "imageUrl": "docker.io/vincentgillet12/critik-mysql-app:latest"}'
                '''
            }
        }

        stage('Deploy to Render') {
            parallel {
                stage('Deploy Angular to Render') {
                    steps {
                        sh '''
                            curl --request POST 
                            --url "https://api.render.com/v1/services/$RENDER_SERVICE_ID_ANGULAR/deploys" 
                            --header 'accept: application/json' 
                            --header "authorization: Bearer $RENDER_API_TOKEN" 
                            --header 'content-type: application/json' 
                            --data "{"clearCache": "clear", "imageUrl": "$DOCKER_REGISTRY/critik-angular-app:$BUILD_NUMBER" }"
                        '''
                    }
                }
                
                stage('Deploy Spring to Render') {
                    steps {
                        sh '''
                            curl --request POST 
                            --url "https://api.render.com/v1/services/$RENDER_SERVICE_ID_SPRING/deploys" 
                            --header 'accept: application/json' 
                            --header "authorization: Bearer $RENDER_API_TOKEN" 
                            --header 'content-type: application/json' 
                            --data "{"clearCache": "clear", "imageUrl": "$DOCKER_REGISTRY/critik-spring-app:$BUILD_NUMBER" }"
                        '''
                    }
                }
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