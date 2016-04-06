Mini FTP is a file transfer service: the client sends to the server the name
 of a file (text format) taken from the command line. The server responds by 
sending the client the contents of the file, line by line. The customer saves 
the contents to a local file with the same name. Error situations 
are handled (file not found, etc). The server is able to handle multiple 
clients at a time.