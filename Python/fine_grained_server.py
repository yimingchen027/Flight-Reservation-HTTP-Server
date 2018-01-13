import time
import os
import threading
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from SocketServer import ThreadingMixIn

HOST_NAME = 'localhost'
PORT_NUMBER = 8080
file_dir = os.getcwd() + '/serverfiles/'
log = []
flights_schedule = {}
flights_schedule_lock = threading.RLock()

# convert the file into filght dict
class Flight():
	def __init__(self, flightID):
		self.flightID = flightID
		self.schedule_info = {}
		self.lock = threading.RLock()

	def add_scedule_info(self, date, time, ticket):
		self.info = [time, ticket]
		self.schedule_info[date] = self.info

	def add_reservation(self, date):
		self.lock.acquire()
		try:
			if self.schedule_info[date][1] >= 1:
				self.schedule_info[date][1] -= 1
				return 'Reserve Success'
			else:
				return 'Not Enough Tickets'
		finally:
			self.lock.release()

def get_flights(filename):
	file_in = open(file_dir + filename)
	flights = {}
	for line in file_in:
		line = line.strip('\n\r')
		line_list = line.split(',')
		if not line_list[0] in flights:
			flights[line_list[0]] = Flight(line_list[0])
			flights[line_list[0]].add_scedule_info(line_list[1], line_list[2], 200)
		else:
			flights[line_list[0]].add_scedule_info(line_list[1], line_list[2], 200)
	return flights

# concurrent server
class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
	pass

class eachLog:
	ipaddress = ''
	dateandtime = ''
	requiredfile = ''
        
class MyHandler(BaseHTTPRequestHandler):
	def do_HEADS(self):
		self.send_response(200)
		self.send_header("Content-type", "text-html")
		self.end_headers()

	def do_GET(self):
		self.send_response(200)
		self.send_header("Content-type", "text-html")
		self.end_headers()
		query = self.path.split('-')

		# create log
		mylog = eachLog()
		mylog.ipaddress = self.client_address[0]
		mylog.dateandtime = self.log_date_time_string()
		mylog.requiredfile = self.path
		log.append(mylog)

		if query[0] == 'Query':
			flight_info = flights_schedule[query[1]].schedule_info[query[2]]
			res = ' '
			self.wfile.write(flight_info)
		if query[0] == 'Order':
			res = flights_schedule[query[1]].add_reservation(query[2])
			self.wfile.write(res)

if __name__ == '__main__':
	# get flight dict
	flights_schedule = get_flights('flights.csv')
	# print the schedule info
	for key in flights_schedule.keys():
		print key + " " + str(flights_schedule[key].schedule_info)
	print '\n'
	server_class = ThreadedHTTPServer
	# if want to test the sequential http server
	# server_class = HTTPServer
	my_server = server_class((HOST_NAME, PORT_NUMBER), MyHandler)
	try:
		my_server.serve_forever()
	except KeyboardInterrupt:
		print '\n'
		for i in log:
			print 'log:'+'  '+i.ipaddress+'  '+i.dateandtime+'  '+i.requiredfile
	print '\n'
	for key in flights_schedule.keys():
		print key + " " + str(flights_schedule[key].schedule_info)
	my_server.server_close()