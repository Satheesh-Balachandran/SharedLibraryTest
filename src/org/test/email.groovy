#!/usr/bin/groovy
package org.test

def test() {
	def NOTIFYUSER
	echo "***** Inside me *****"
	 wrap([$class: 'BuildUser']) {
		 if(env.BUILD_USER_EMAIL != null) {
			 echo "****Manual Trigger******"
			NOTIFYUSER = "${BUILD_USER_EMAIL}"
		 }
    	}
	
	if(NOTIFYUSER == null) {
		echo "****GitHub Commit****"
		NOTIFYUSER = bat (
      			script: 'git --no-pager show -s --format=\'%ae\'',
   			returnStdout: true
		).trim()
	}
	//Make the build unstable
	currentBuild.result = 'UNSTABLE'
	//Notify the user who triggered the build(Manual Build or Git commit)
	emailext body: "Hello", subject: "Success", to: "${NOTIFYUSER}"
}
