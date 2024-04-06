def lintchecks() {
    sh '''
        echo Installing Lint Checker
        npm i jslint
        echo Performing Lint Checks for $COMPONENT
        node_modules/jslint/bin/jslint.js server.js || true
    ''' 
}

def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner  -Dsonar.host.url=http://172.31.80.115:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
}


def call(COMPONENT) {
    pipeline { 
        agent any
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
                        sonarchecks()
                    }
                }
            }
        }
    }
}
