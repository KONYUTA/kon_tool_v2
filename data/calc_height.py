import sys
import requests

args = sys.argv

with open(args[1]) as f:
    for lines in f:
        # print(lines)
        line = lines.split(",")
        (x,y) = (line[1],line[0])
        url = "http://cyberjapandata2.gsi.go.jp/general/dem/scripts/getelevation.php" \
              "?lon=%s&lat=%s&outtype=%s" %(x, y, "JSON")
        resp = requests.get(url, timeout=10)
        data = resp.json()
        print(data["elevation"])
