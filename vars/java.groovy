def lintchecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}

def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner  -Dsonar.host.url=http://172.31.80.115:9000 -Dsonar.java.binaries=./target/ -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password
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

            stage('Compiling Java Coe') {
                steps {
                    sh "mvn clean compile"                          // For java based projects , you always need to supply binary/class files
                    sh "ls -ltr target/"
                }
            }

            stage('Static Code Analysis') {
                steps {
                    script {
                        env.ARGS="-Dsonar.java.binaries=./target/"
                        sonarchecks()
                    }
                }
            }
        }
    }
}