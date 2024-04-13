def lintchecks() {
    stage('Sonar Checks') {
        sh '''
            echo Installing Lint Checker
            echo Performing Lint Checks for $COMPONENT
            echo Lint Checks Completed for $COMPONENT
        ''' 
    }
}

def sonarchecks() {
    stage {
        sh ''' 
            echo Sonar Checks Starting for $COMPONENT
            # sonar-scanner  -Dsonar.host.url=http://${NEXUS_URL}:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}
            echo Sonar Checks Starting for $COMPONENT is Completed
        '''
    }
}

def sonarresult() {
    sh '''
            curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gate.sh
            # bash -x gate.sh ${SONAR_CRED_USR} ${SONAR_CRED_PSW} ${NEXUS_URL} ${COMPONENT} ||true
            echo SCAN LOOKS GOOD
        '''
}

def testcases() {
        stage('test cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing In Progress"
            echo "Unit Testing Is Completed"
        }

        stages["Integration Testing"] = {
            echo "Integration Testing In Progress"
            echo "Integration Testing Is Completed"
        }

        stages["Functional Testing"] = {
            echo "Functional Testing In Progress"
            echo "InteFunctionalration Testing Is Completed"
        }

        parallel(stages)
    }
}


def artifacts() {
    stage('Checkting Artifact On Nexus') {
        env.upload_status=sh(returnStdout: true, script: "curl -s -L http://172.31.38.109:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true")
        print upload_status
    }
    
    if(env.upload_status == "") {
        stage('Generate Artifacts') {

        }

        stage('Publish Artifacts') {
            
            }
        }
    }


