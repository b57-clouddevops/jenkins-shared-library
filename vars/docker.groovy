def call() {
    node {
        git branch: 'main', url: "https://github.com/b57-clouddevops/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.java.binaries=./target/"
        // common.sonarchecks()
        common.testcases()
        if(env.TAG_NAME != null) {
            stage('Generating & Publishing Artifacts') {
            if(env.APPTYPE == "nodejs") {
                    sh "echo Generating Node Artifacts"
                    sh "npm install"               
                }         
            else if(env.APPTYPE == "python") {
                    sh "echo Generating Artifacts"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt"        
                }
            else if(env.APPTYPE == "maven") {
                    sh "mvn clean package"
                    sh "mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip  ${COMPONENT}.jar"      
                }
            else if(env.APPTYPE == "angularjs") {
                      cd static/
                      zip -r ../${COMPONENT}-${TAG_NAME}.zip *
                }
            else { sh "echo Selected Component Type Doesnt Exist" }                        
        }
            sh "echo Downloading the pen key file for DB Connectivity"
            sh "wget https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem"
            sh "docker build -t 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME} ."
            sh "docker push 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
        }
    }
}