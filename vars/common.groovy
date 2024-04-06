def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner  -Dsonar.host.url=http://172.31.80.115:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
}

