// Jenkinsfile - DX Golden Path v0.1

// Jenkins Shared Libraries

// 1 - helm charts
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-helm-charts_nix@master', retriever: modernSCM([$class: 'GitSCMSource',
remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-helm-charts_nix.git',
credentialsId: 'github_token'])

// 2 - TruffleHog
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-trufflehog_nix@master', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-jenkins-sharedlib-trufflehog_nix.git',
   credentialsId: 'github_token'])

// 3 - SonarQube
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-sonarQube_multi@master', retriever: modernSCM([$class: 'GitSCMSource',
remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-sonarQube_multi.git',
credentialsId: 'github_token'])

// 4 - Dockerbuild (Podman)
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-dockerbuild@feature/ocir-change', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-dockerbuild.git',
   credentialsId: 'github_token'])

// 5 - Changelog (Semantic Versioning)
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-changelog@master', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-changelog.git',
   credentialsId: 'github_token'])

// 6 - ZAP
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-owasp-zap-scan-jenkins-sharedlib@master', retriever: modernSCM([$class: 'GitSCMSource',
remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-owasp-zap-scan-jenkins-sharedlib.git',
credentialsId: 'github_token'])

// 7 - Terraform
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-terraform@feature/oci-tf-shared-lib', retriever: modernSCM([$class: 'GitSCMSource',
   remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-terraform.git',
   credentialsId: 'github_token'])

// 8 - Release Lifecycle
library identifier: 'dx-golden-path-v0.1-neom-jenkins-sharedlib-release-lifecycle@master', retriever: modernSCM([$class: 'GitSCMSource',
remote: 'https://github.com/NEOM-KSA/dx-golden-path-v0.1-neom-jenkins-sharedlib-release-lifecycle.git',
credentialsId: 'github_token'])

// Jenkins Pipeline
pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: podman
            imagePullPolicy: Always
            image: jed.ocir.io/axnfm4jb3i73/podman-trivy-oci:latest
            command:
            - cat
            tty: true
            securityContext:
              privileged: true
            volumeMounts:
            - mountPath: /var/lib/containers
              name: podman-volume
            - mountPath: /dev/shm
              name: devshm-volume
            - mountPath: /var/run
              name: varrun-volume
            - mountPath: /tmp
              name: tmp-volume
          - name: trufflehog
            image: jed.ocir.io/axnfm4jb3i73/trufflehog1:latest
            command:
            - cat
            tty: true
          - name: ubuntu
            image: jed.ocir.io/axnfm4jb3i73/ubuntu1:latest
            command:
            - cat
            tty: true
          - name: git
            image: jed.ocir.io/axnfm4jb3i73/git1:latest
            command:
            - cat
            tty: true
          - name: build
            image: jed.ocir.io/axnfm4jb3i73/gradlejdk11:latest
            command:
            - cat
            tty: true
          - name: build1
            image: docker.io/maven:3.9.0
            command:
            - cat
            tty: true			
          - name: helm
            image: jed.ocir.io/axnfm4jb3i73/helm-oci:latest
            command:
            - cat
            tty: true
          - name: owasp-zap
            image: jed.ocir.io/axnfm4jb3i73/zap:latest
            command:
            - cat
            tty: true
          - name: changelog
            image: jed.ocir.io/axnfm4jb3i73/changlog1:latest
            command:
            - cat
            tty: true
          - name: mysql
            image: jed.ocir.io/axnfm4jb3i73/mysql:latest
            command:
            - cat
            tty: true
          restartPolicy: Never
          volumes:
          - name: podman-volume
            emptyDir: {}
          - emptyDir:
            medium: Memory
            name: devshm-volume
          - emptyDir: {}
            name: varrun-volume
          - emptyDir: {}
            name: tmp-volume
          imagePullSecrets:
          - name: ocirsecret
        '''
    }
  }

  environment{
    userpat= credentials('git_pass')

    // nexus_username = credentials('nexus_sbx_username')
    // nexus_password = credentials('nexus_sbx_password')
    // DB_USERNAME = credentials('db_username')
    // DB_PASS = credentials('db_pass')
    // DB_HOST=credentials('db_host')
    // REDIS_PASSWORD= credentials('redis-password')

    def environmentName = 'dx-golden-path-build'
    def tenancyNamespace = 'axnfm4jb3i73' // newly defined for ocir push
    def projectName = 'dx-golden-path'
    // def releaseName = 'dx-golden-path-release1'
    // def PROJECT_URL = "https://github.com/NEOM-KSA/dx-golden-path-v0.1.git"
    // GIT_URL = "https://github.com/NEOM-KSA/dx-golden-path-v0.1-jenkins-sharedlib-trufflehog_nix.git" // GIT_URL for TruffleHog - Example: "https://$GIT_HUB@github.com/mobilityhouse/testci.git" // newly defined for TruffleHog
    // def namespace = "dx-golden-path-namespace"
    def imagetype = "dx-golden-path-v0.1"
    def compartment_id='ocid1.compartment.oc1..aaaaaaaaeg7m7tc6lbn6ca4c22dddfwhzssp2mfsg676nszu2kexf4grs4uq'
    def tag = "1.0.17" // tag for docker image (podman)
    def version = "1.0.17"
    def BRANCH_NAME = "master"
  }

  stages {

    // 1 - Truffle Hog
    stage('Run Repo Scan') {
          steps {
            echo 'h1'
            echo 'BRANCH NAME:' + env.BRANCH_NAME
            echo 'GIT_URL:' + env.GIT_URL
            sh "echo ${env.BRANCH_NAME}"
            container('trufflehog') {
                repoScan([branchName: env.BRANCH_NAME])
	    }
      }
    }

    // 2 - Clean Up
    stage('Workspace clean') {
          steps {
            echo 'Stage Cleanup Workspace ...'
            // Cleanup before starting the stage
            cleanWs()
       }
    }

    // 3 - Semantic Versioning (SemVer)
    stage('Checkout Code') {
          steps {
                echo 'SemVer ...'
                script{
                  container('git') {
                    echo 'inside git(changelog) container ...'
                    sh 'hostname'
                    checkout scm
                        // (tag,flag) = semverVersion()
                        // env.version = tag
                      }
                  }
                }
          }

    // // 4 - Run PreReq
    // stage('Run Prerequisite Tasks') {
    //         when {
    //          	      branch 'master'
    //            }
    //    steps {
    //         script {
    //              def config = readYaml  file: 'helm/neom-fss-neompay-wallet-api/values.yaml'
    //                 for(env_var in config.env){
    //                       if(env_var.name == 'DB_NAME'){
    //                          data_base_name=env_var.value;
    //                             env.APP_DATABASE_NAME = data_base_name
    //                         }
    //                  }
    //             }
    //         script{
    //            def data_base_name=''
    //            container('mysql') {
    //               sh(script:'''
    //                 mysql -N -u $DB_USERNAME -p$DB_PASS -h $DB_HOST -e "CREATE DATABASE IF NOT EXISTS $APP_DATABASE_NAME"
    //                 mysql -N -u $DB_USERNAME -p$DB_PASS $APP_DATABASE_NAME -h $DB_HOST <library/sql/CREATE_DATABASECHANGELOG.sql
    //               ''')
    //         }
    //     }
    //   }
    // }

    // 5 - Sonar Quality Report
    stage('Sonar Quality Report') {
      steps {
        container('build') {
            withSonarQubeEnv('sonar-server') {
              checkout scm
	       sh 'pwd&& ls -lrth'
              sh '''
               PATH=$PATH:/home/jenkins/agent/workspace/test-UD:.
	       ls -lrth /home/jenkins/agent/workspace/test-UD/gradlew && pwd
	       hostname
	       whoami
	       sleep 20001
                find / -name gradlew 2>/dev/null
               ./gradlew  -Dmaven.multiModuleProjectDirectory=/home/jenkins/agent/workspace/test-UD/Ug-Customer-Preference-Management/Ug-Customer-Preference-Management sonarqube
              '''
        }
      }
    }
    }

    // 6 - Sonar Quality Gate
    stage('Quality Gate') {
      steps {
        container('ubuntu') {
            qualityGates()
        }
      }
    }

    
    // 7 - Code Build
    stage('Code Build') {
      steps {
        container('build1') {
          checkout scm
          sh 'echo "BEFORE CODE BUILD"'
	  sh 'pwd&& ls -lrth'
          sh 'mvn  -Dmaven.multiModuleProjectDirectory=/home/jenkins/agent/workspace/Ug-Customer-Preference-Management/Ug-Customer-Preference-Management clean install'
		sh "pwd && cd build && ls -lrth"
		sh "date && ls -lrth "
        }
      }
    }

   
	}
}
