pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        ODH_URL = "https://tourism.api.opendatahub.bz.it/v1"
        ODH_AUTH_URL = "https://auth.opendatahub.bz.it/auth/realms/noi/protocol/openid-connect/token"
        ODH_AUTH_CLIENT_ID = "odh-api-core-alpinebits"
        ODH_AUTH_CLIENT_SECRET = credentials('odh-authserver-tourism-api-prod-clientsecret')
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s%\\(odh.url\\s*=\\).*\\$%\\1${ODH_URL}%" application-war/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(odh.auth.url\\s*=\\).*\\$%\\1${ODH_AUTH_URL}%" application-war/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(odh.auth.client.id\\s*=\\).*\\$%\\1${ODH_AUTH_CLIENT_ID}%" application-war/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(odh.auth.client.secret\\s*=\\).*\\$%\\1${ODH_AUTH_CLIENT_SECRET}%" application-war/src/main/resources/application.properties'
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
                archiveArtifacts artifacts: 'application-war/target/ROOT.war', onlyIfSuccessful: true
            }
        }
    }
}
