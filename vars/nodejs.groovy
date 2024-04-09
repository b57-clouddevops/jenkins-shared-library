def lintchecks() {
    sh '''
        echo Installing Lint Checker
        npm i jslint
        echo Performing Lint Checks for $COMPONENT
        node_modules/jslint/bin/jslint.js server.js || true
    ''' 
}

def call(COMPONENT) {
    pipeline { 
        agent any
        environment {
            NEXUS_URL   ="172.31.80.115"
            SONAR_CRED  = credentials('SONAR_CRED')
        }
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintchecks()
                    }
                }
            }

            stage('Static Code Analysis') {
                steps {
                    script {
                        env.ARGS=" -Dsonar.sources=."
                        common.sonarchecks()
                    }
                }
            }
  
            stage('Get Sonar Result') {
                steps {
                    script {
                        common.sonarresult()
                    }
                }
            }

            stage('Testing') {
                steps {
                    sh "echo Testing In Progress"
                }
            }

        stage("Testing") {
            parallel {
                stage("Unit Testing") {
                    steps {
                        sh "echo Unit Testing In Progress"
                        //sh "npm test"
                        sh "sleep 60"                
                    }
                }
                stage("Integration Testing") {
                    steps {
                        sh "echo Integration Testing In Progress"
                        //sh "npm verify"
                        sh "sleep 60"                
                    }
                }
                stage("Functional Testing") {
                    steps {
                        sh "echo Functional Testing In Progress"
                        sh "sleep 60"                
                    }
                }
            }
        }
        }
    }
}
