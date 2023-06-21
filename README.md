# BSDS-project2

## Available Scripts

After unzipping the project, type this in the terminal

```
cd out/artifacts   :   to enter the folder where all the jar files are present
```

### Starting the server

```
java -jar Server.jar <ip-address> <port>
```

### Starting the Client

```
java -jar Client.jar <ip-address> <port>
```

Once the two commands have been typed, you can start sending requests via the client. Requests are
tab separated which means you have to send the request in the following format

```
GET \t+ key
PUT \t+ key \t+ value can be space separated
DELETE \t+ key can also be space seprated      
```

Here `\t+` denotes one or more tab key presses. If you use space instead of tab, then the requests
will throw errors

### To check the screenshots uploaded as part of this assignment

Do this step if you are in `artifacts` directory

```
cd ../screenshots
```

### Please check `Summary.md` in the `out` directory for key learnings.
