def lintchecks() {
        sh "echo Performing Lint Checks for $COMPONENT"
        // sh "pip3 install pylint && pylint *.py"
        sh "echo Style Checks Completed $COMPONENT"
}

def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner  -Dsonar.host.url=http://172.31.80.115:9000 -Dsonar.sources=. -Dsonar.projectKey=user -Dsonar.login=admin -Dsonar.password=password
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



