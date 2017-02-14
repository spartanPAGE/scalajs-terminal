import os
from distutils.dir_util import copy_tree

print("Just give me a sec'")

os.system("sbt fastOptJS")
copy_tree("assets", "docs/assets")
copy_tree("target", "docs/target")            

print("done!")
print("now go on with your git add . and blah bah")
