# Web Crawler project

This project was created for coding exercise purposes.  

Author: Przemyslaw Ozimkiewicz

## Build
You need to have Maven 3 and Java 8 installed to build and run this solution.
 
```bash
mvn clean compile test assembly:single
```


## Run
```bash
java -jar target/web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar https://wiprodigital.com

cat urls_wiprodigital.com.txt
```

As a result of crawling the map of discovered links is written to output file (urls_domain.name.txt) in current directory.

## Description of solution and crawling process

After opening entry URL i.e. https://www.wiprodigital.com crawler looks for all HTML links within the document. 
Found links  are added to sitemap (unless they are in sitemap already) with information about parent(parent url).
Every iteration looks for stored but yet not visited URLs and performs the same lookup for child URLs.   
Implemented data structure allows to have only one parent so child URLs are presented only under one parent (the first that lead to uniqie child) 
even if they occur under multiple parent URLs. Iteration is finished when all found unique "in domain" links were visited.
   
Only links within provided domain are visited. For details see ExternalUrlTest.java JUnit class.


HTML processing and lookup for ```a[href]``` is done by Jsoup library. 

For logging purposed logback logging framework was used.  


## Possible improvements:

Having more time I would like to implement few of following improvements:

 - handling of HTTP 5XX and network IO exceptions, implementation of limited number of retires for such errors
 - better regexp for detecting urls for the same domain
 - presentation of discovered urls in XML or JSON format
 - crawling stop conditions (timeout or maximum number of entries in map) 
 - more junits for better coverage
 - get rid of recursive usage of printChilds method