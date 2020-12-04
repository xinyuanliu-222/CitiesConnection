# Checking Connetion between Two Cities

This application is used to determine if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.

Data: List of roads is available in a file. The file contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.

Goal: Your program should respond with ‘yes’ if city1 is connected to city2, ’no’ if city1 is not connected to city2. Any unexpected input should result in a ’no’ response.

## Model
We need to find all the cities and its neighbors cities from the input data. So I created a City model which consist of 2 fileds, one is city name and the other is list of its neighbor cities. We set 2 cities are equal if their name is same. To avoid case sensitive problem, I transfer all the name to lower case.

## Repository
I used a repository Graph to construct the City model. I read each line of the text data and split them as 2 cities name. I used a Map to store the city name and its related city, so that we can add its neighbor once we found a city with same name.

## Service
After obtaining city data, I used a service model to accomplish the business logic of this application. That is to chceck whether 2 cities are connected. There can be 3 types of connection:
* 2 same cities are connected.
* 2 cities are directly connected - which means one city is another city's neighbor.
* 2 cities are undirectly connected - which mean city A connected with city B, city B connected with city C, so city A connected with city C.

The first and second type connection can be check easily. To check the third type connection, I used a Set array and Stack. Set is used to record all the visited cities to avoid duplicate computing and save time. Stack is used to store all neighbors af origin city and its neighbor's neighbors. I used a while loope to loop thright all the neighbors of origin, if destination doesn't exist, I continously add origin's neighbors untill all the cities are visited.

## Controller
After finishing business logic, we just implement it to the REST controller, based the path parameters of URL to decide whether they are connected. Return "yes" if connected and "no" if not connected or unexpected input.

