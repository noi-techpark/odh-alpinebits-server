pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        ODH_URL = "https://tourism.opendatahub.bz.it"
    }

    stages {
        stage('Configure') {
            steps {
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
                sh 'mvn -B -U clean package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/ROOT.war', onlyIfSuccessful: true
            }
        }
    }
}
