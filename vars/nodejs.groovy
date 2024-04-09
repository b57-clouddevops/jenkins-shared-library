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
                    sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gate.sh"
                    sh "bash gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${NEXUS_URL} ${COMPONENT}"
                    sh "echo SCAN LOOKS GOOD"
                }
            }

            stage('Testing') {
                steps {
                    sh "echo Testing In Progress"
                }
            }
        }
    }
}
