import xml.etree.ElementTree as ET
import sys,os

###
## Python Script to build Android applications that have generated R.java files
## when built.
## 07/2015
###

def appBuild(appDir):
	start_path = os.getcwd()
	print "Changing to app Directory...."
	os.chdir(appDir)
	print "Building Android app...."
	os.system("./gradlew assembleDebug -q")
	#Reset current directory
	print "Moving back to android-tutorials root directory..."
	os.chdir(start_path)


	
def correct_usage():
	print "\n**** Invalid Arguments ****\n"
	print "Correct usage:\n"
	print "  ./xmlParse.py filename\n"
	print "    filename - name of xml file\n"
	sys.exit(-1)


def run():
	if(len(sys.argv) != 2):
		correct_usage()
		
	tree = ET.parse(str(sys.argv[1]))
	root = tree.getroot()
	base_path = ""
	for itm in root.iter('application'):
		for subitm in itm.iter('file'):
			if 'R.java' in subitm.attrib['name'] and 'build/generated' in subitm.attrib['name']:
				# build App since it has an R resource file
				base_path = itm.find('files').attrib['base']
				appBuild(base_path)	
if __name__ == "__main__":
	run()
