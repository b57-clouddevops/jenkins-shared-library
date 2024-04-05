def lintchecks() {
    sh "echo Installing Lint Checker"
    sh "npm i jslint"
    sh "echo Performing Lint Checks for $COMPONENT"
    sh "node_modules/jslint/bin/jslint.js server.js || true" 
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
                    sh "echo Static Checks ...."
                }
            }
        }
    }
}