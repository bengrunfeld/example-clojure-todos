import httplib, urllib
import json
headers = {"Content-type": "application/json",
    "Accept": "text/plain"}
params = urllib.urlencode({'@number': 12524, '@type': 'issue', '@action': 'show'})
conn = httplib.HTTPConnection("localhost", 3000)

data = { "name" : "grocery", "task" : "get bread" }

print "INSERTING"
conn.request("PUT", "/todos/", json.dumps(data))
response = conn.getresponse()
print response.status, response.reason, json.loads(response.read())

print "GETTING"
conn.request("GET", "/todos/" )
response = conn.getresponse()
as_str = response.read()
print "json ", json.loads(as_str)


print "INSERTING"
conn.request("PUT", "/todos/", json.dumps(data))
response = conn.getresponse()
doc = json.loads(response.read())
print response.status, response.reason, doc

print "DELETING"
conn.request("DELETE", "/todos/" + doc["_id"])
response = conn.getresponse()
print response.status, response.reason, response.read()
