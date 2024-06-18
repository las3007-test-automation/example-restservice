pipeline {
    agent none

    stages {
        // The first four stages run on every commit on every branch
        // These are usually prerequisites for merging a feature/bugfix branch to mainline
        // To conserve computing resources once might opt to restrict these stages to execute only when there is a pull request or merge request
        stage('Build') {
        	echo 'Running Build ...'
            //agent {
                //docker {
                    //image 'maven:3.9-eclipse-temurin-21'
                    //args '-v /root/.m2:/root/.m2'
                //}
            //}
            //steps {
                //echo 'Building ...'
                //sh 'mvn -B -DskipTests clean package'
            //}
            //post {
                //success {
                   // archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                //}
            //}
        }

        stage('Unit Test') {
        	echo 'Running Unit Test ...'
            //agent {
                //docker {
                    //image 'maven:3.9-eclipse-temurin-21'
                    //args '-v /root/.m2:/root/.m2'
                //}
            //}
            //steps {
                //echo 'Running Unit Tests ...'
                //sh 'mvn test'
            //}
            //post {
                //always {
                    //junit '**/target/surefire-reports/*.xml'
                //}
            //}
        }

        stage('Static Code Analysis') {
            // Static code analysis for code smells or test coverage via tools like JacCoCo which generate reports
            // These reports are usually pushed to Cobertura, Sonar or might be directly processed via a Jenkins plugin
            agent any
            steps {
                echo 'Running Static Code Analysis ...'
            }
        }

        stage('Security Scanning') {
            // Security tools scan the code for vulnerabilities or cross check dependency versions for known vulnerabilities
            // These vulnerabilities are usually published in CVE databases
            agent any
            steps {
                echo 'Running Security Scanning ...'
            }
        }

        stage('Package and Publish Artifacts') {
        	 echo 'Package ...'
            //when {
                //anyOf {
                    //branch 'main';
                    // Artifacts should also be packaged and published when a new tag is discovered
                    //buildingTag()
                //}
            //}
            //agent {
                //docker {
                    //image 'docker:25.0-dind'
                //}
            //}
            //steps {
                //echo 'Package ...'
                //sh 'docker build ./rest-service -t rest-service:$GIT_COMMIT'
                // Usually the image would get uploaded to a docker registry to keep a history of all images which get deployed
                // This allows for rollbacks, audit trails, troubleshooting and old version, etc.
                // sh 'docker push rest-service:$GIT_COMMIT'
            //}
        }

        stage('Dev Environment') {
            when {
                anyOf {
                    branch 'main';
                    // Dev deployment and testing should also run when a new tag is discovered
                    buildingTag()
                }
            }
            stages {
                //stage('Dev Deploy') {
                    //agent {
                        //docker {
                            //image 'docker:25.0-dind'
                        //}
                    //}
                    //steps {
                        //echo 'Deploying to Dev ...'
                        // Stopping the application means that we will have a downtime
                        // Usually the application would be deployed in a blue-green fashion
                        // Another option would be to run the application in high-availability with graceful shutown
                        //echo 'Stopping older version of rest-service if it is running'
                        //sh 'docker rm $(docker stop $(docker ps -a -q --filter name=rest-service --format="{{.ID}}")) || true'

                        // Here the application is running in a container within the same environment which hosts Jenkins
                        // This would usually be running on a dev Kubernetes cluster and deployed with something like:
                        // 
                        // 1) Vanilla Kubernetes
                        // sh 'sed -i "s|{VERSION}|$GIT_COMMIT|g k8s.yaml'
                        // sh 'kubectl apply -f k8s.yaml'
                        //
                        // 2) Kubernetes using the kustomize extensions
                        // sh 'kubectl apply -f kustomization.yaml'
                        //
                        // 3) Kubernetes with Helm
                        // sh 'helm upgrade rest-service --version $GIT_COMMIT --values values.yaml'
                        //
                        // For the sake of simplicity, here we are deploying to Docker instead
                        //echo "Deploying and starting rest-service:$GIT_COMMIT"
                        //sh 'docker container run -d -p 8081:8081 --name rest-service --network=test-automation-demo rest-service:$GIT_COMMIT'
                    //}
                //}
                stage('Dev Integration Tests') {
                    failFast true
                    // Certain stages of the pipeline can be run in parallel
                    parallel {
                        stage('Dev Acceptance Tests') {
                            agent {
                                docker {
                                    image 'maven:3.9-eclipse-temurin-21'
                                    args '-v /root/.m2:/root/.m2 --network=test-automation-demo'
                                }
                            }
                            steps {
                                echo 'Running Acceptance Tests on Dev ....'
                                sh 'mvn verify -Pacceptance-tests'
                            }
                            post {
                                always {
                                    junit '**/target/failsafe-reports/*.xml'
                                }
                            }
                        }
                        stage('Dev Performance Tests') {
                            agent any
                            steps {
                                echo 'Running Performance Tests on Dev ...'
                            }
                        }
                    }
                }
            }
        }

        stage('Prod Environment') {
            when {
                // Git tags can be used as manual gatekeepers
                // Even though every artifact would have been tested on dev and is a candidate for prod deployment
                // Certain organizations might prefer to choose when/what to deploy to prod for various reasons
                buildingTag()
            }
            stages {
                stage('Prod Deploy') {
                    agent any
                    steps {
                        echo 'Deploying to Prod ...'
                    }
                }
                stage('Prod Sanity Check') {
                    agent any
                    steps {
                        echo 'Running Smoke Tests on Prod ...'
                    }
                }
            }
        }
    }
}
