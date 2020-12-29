# ExpenseReimbursementApp

This system allows for quick and effecient handling and viewing of expense reimbursement requests. Managers are able to view any submitted requests, and can filter those requests by a single employee or by pending status. Managers can also approve or deny any pending requests. Employees are able to submit new requests, and they are able to see the status of all of their previous requests.

On the backend, the org.revature.munderhill.expensereimbursementapp package consists of:
- An authenticationfilters package, which includes a web filter class that checks that a user has authenticated (checks for valid Cookie)
- A DAOs (Data Access Objects) package that includes classes that manage persistence
- An entities package, which includes the Hibernate entities used for persistence
- A hibernate package, which includes a singletion HibernateConnectionUtility class that allows for data access objects to obtain Hibernate Session interfaces to connect to the database
- A models package, which includes a class that is used to format the authentication cookie in JSON
- A servlets package, including servlets that process Http requests at certain endpoints

Other important backend files/folders include:
- A hibernate.cfg.xml file in the src/main resources folder, which is not in the github repository, but contains information to connect to the Amazon RDS used in this project
- A log4j2.properties folder, used to configure logging for this project
- A deployment descriptor (web.xml) file in the src/main/webapp/WEB-INF folder
- Various test classes in the src/test folder
- The src/db folder, which provides the database schema and seed data scripts for this application

The front end javascript, html and css files reside in the src/main/webapp folder. More specifically:
- The /js folder includes the javascript used to make asynchronous calls with the Fetch API and to manipulate the DOM
- The /resources folder includes css files, including the bootstrap css library
- The root folder (/webapp) contains html files that display content



