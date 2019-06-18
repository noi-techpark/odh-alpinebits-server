pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        TESTSERVER_TOMCAT_ENDPOINT = "http://alpinebits-server.tomcat02.testingmachine.eu:8080/manager/text"
        TESTSERVER_TOMCAT_CREDENTIALS = credentials('testserver-tomcat8-credentials')
        ODH_URL = "https://tourism.opendatahub.bz.it"
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        ${TESTSERVER_TOMCAT_CREDENTIALS}" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'
                sh 'echo "</settings>" >> ~/.m2/settings.xml'

                sh 'sed -i -e "s%\\(odh.url\\s*=\\).*\\$%\\1${ODH_URL}%" application-spring/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(odh.url\\s*=\\).*\\$%\\1${ODH_URL}%" application-war/src/main/resources/application.properties'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B -U clean test verify -P it'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -U clean install'
                sh 'cd application-war && mvn -B -U clean package'
            }
        }
        stage('Deploy') {
            steps{
                sh 'cd application-war && mvn -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT} -Dmaven.tomcat.server=testServer'
            }
        }
    }
}
