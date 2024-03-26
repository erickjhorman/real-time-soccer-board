Run app 

1. Clone the project
2. Run Service Discovery Module
3. Run Consumer & Producer modules
4. Go to localhost:8761 to see your registered modules


Enjoy ------ 

**How to push changes**

We're working with GIT flow base development
1. Create a branch like this feature/<name of your branch> from develop and make pushes to it directly 
2. Create a pull request from your branch to develop to add changes to the repo
3. Create a pull request from develop to release if you need to pass changes from develop to release.


Branch strategy
Rebase the remote branch to have a clear commit historic

1. Create a branch from develop and make pushes to it directly 
2. Create a pull request from your branch to develop to add changes to the repo
3. Create a pull request from develop to release if you need to pass changes from develop to release.


Run kafka cluster
1. Installed and start Docker Engine 
2. Go to the root module of the project and create a terminal in that folder and run the next command `docker-compose -f docker-compose.yml up -d` to run kafka cluster
3. go to http://localhost:3040 to see a dashboard with all info about kafka topic and partition
