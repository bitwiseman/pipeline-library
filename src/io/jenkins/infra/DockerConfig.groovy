package io.jenkins.infra


import java.text.SimpleDateFormat
import java.util.Date

class DockerConfig {
  String imageName

  String registry

  String dockerfile

  String credentials

  String mainBranch

  def buildDate

  def infraConfig

  public DockerConfig(String imageName, InfraConfig infraConfig, Map config=[:]) {
    this.imageName = imageName

    this.infraConfig = infraConfig

    this.registry = config.registry

    this.dockerfile = config.get('dockerfile', 'Dockerfile')

    this.credentials = config.get('credentials', 'jenkins-dockerhub')

    this.mainBranch = config.get('mainBranch', 'master')

    this.buildDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date())
  }

  String getFullImageName() {
    return getRegistry() + '/' + imageName
  }

  // Custom getter to avoid declaring NonCPS method called from constructor
  String getRegistry() {
    def reg = registry ?: infraConfig?.dockerRegistry ?: 'noregistry'
    return reg.endsWith('/') ? reg[0..-2] : reg
  }
}
