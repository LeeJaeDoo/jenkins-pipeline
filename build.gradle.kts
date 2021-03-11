plugins {
    groovy
    id("com.mkobit.jenkins.pipelines.shared-library") version "0.10.1"
}

group = "me.godo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        withConvention(GroovySourceSet::class) {
            groovy {
                setSrcDirs(listOf("jenkinsfiles", "vars"))
            }
        }
    }
}

dependencies {
    implementation("org.codehaus.groovy:groovy-all:2.3.11")
    implementation("com.cloudbees.jenkins.plugins", "cloudbees-credentials", "3.3", "", "", "jar")
    implementation ("org.jenkins-ci.plugins:credentials:2.1.13")
    implementation ("org.jenkins-ci.plugins:credentials-binding:1.13")
    implementation ("org.jenkins-ci.plugins:matrix-auth:1.6")
    implementation ("org.jenkins-ci.plugins.workflow:workflow-cps:2.39")
    implementation ("org.jenkins-ci.plugins.workflow:workflow-step-api:2.13")
    implementation ("org.jenkins-ci.plugins.workflow:workflow-job:2.15")
    implementation ("org.jenkins-ci.plugins.workflow:workflow-support:2.16")
    implementation ("org.jenkins-ci.plugins.workflow:workflow-api:2.23.1")
    implementation ("org.jenkins-ci.main:jenkins-core:2.23")

    implementation("org.apache.commons:commons-math3:3.4.1")

    testImplementation(kotlin("test-junit5"))
    testImplementation ("org.spockframework:spock-core:1.1-groovy-2.4")
}
