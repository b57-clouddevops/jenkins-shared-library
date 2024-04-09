def lintchecks() {
        sh "echo Performing Lint Checks for $COMPONENT"
        // sh "pip3 install pylint && pylint *.py"
        sh "echo Style Checks Completed $COMPONENT"
}

def call(COMPONENT) {
    pipeline { 
        agent any
        environment {
            NEXUS_URL="172.31.80.115"
            SONAR_CRED  = credentials('SONAR_CRED')      // SONAR_CRED_USR , SONAR_CRED_PSW
        }
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintchecks()
                    }
                    sh "env"
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
        }
    }
}



