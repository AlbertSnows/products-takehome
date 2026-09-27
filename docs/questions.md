# Questions

High level questions to think about. 

## how is react used in the modern day? 

There are many options, such as Next.js, React Router, and expo. 
For this use case, we can just use a basic system bundled with vite.
In our case, we use a Vite single-page app. 

## how do we set up a local, small, postgres table?

Put it in a docker image and mount the table. 

## how do we set up java in a sane and repeatable way? 

Spring boot has a nice system for this. The main considerations are:
1. Pin the JDK, 25 works fine
2. Pin the build tool, I like gradle 
3. Pin the spring boot version