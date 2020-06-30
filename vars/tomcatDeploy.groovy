def call(config){
  def credId = config['credId']
  def ip = config['ip']
  def userName = config['userName']
  def tomcatHome = config['tomcatHome']
  def warName = config['warName']
  
  sshagent([credId]) {
                   // stop the server
                   sh "ssh -o StrictHostKeyChecking=no ${userName}@${ip} ${tomcatHome}/bin/shutdown.sh"
                   // delete old wafile
                   sh "ssh ${userName}@${ip} rm -rf ${tomcatHome}/webapps/${warName}*"
                   // copy latest war file to tomcat-dev server
                   sh "scp  target/springmvc.war ${userName}@${ip}:${tomcatHome}/webapps/"
                   // start the server
                   sh "ssh ${userName}@${ip} ${tomcatHome}/bin/startup.sh"
   }  
}
