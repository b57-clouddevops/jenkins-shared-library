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
                        common.sonarchecks()
                    }
                }
            }
        }
    }
}
