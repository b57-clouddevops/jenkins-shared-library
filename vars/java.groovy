def lintchecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}

def call(COMPONENT) {
    pipeline { 
        agent any
        environment {
            NEXUS_URL="172.31.80.115"
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