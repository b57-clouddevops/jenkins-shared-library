def lintchecks() {
    sh "echo Performing Lint Checks for $COMPONENT"
    //sh "mvn checkstyle:check || true"     // Uncomment this if you want to perform lintChecks
    sh "echo Lint Checks Completed for $COMPONENT"
}

def call(COMPONENT) {
    pipeline { 
        agent any
        environment {
            NEXUS_URL = "172.31.38.109"
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

            stage("Testing") {
                steps {
                    script {
                        common.testcases()
                    }
                }
            }

        }
    }
}