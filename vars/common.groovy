def sonarchecks() {
    sh ''' 
        echo Sonar Checks Starting for $COMPONENT
        sonar-scanner  -Dsonar.host.url=http://{NEXUS_URL}:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password
        echo Sonar Checks Starting for $COMPONENT is Completed
     '''
}
