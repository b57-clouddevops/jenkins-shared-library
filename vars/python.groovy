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
        }
    }
}



