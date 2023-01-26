To start this program run this command:

java FindPrimes



This program uses an algorithm known as the Sieve of Eratosthenes to find all the primes below 10^8. 

To split the algorithm across multiple threads, I assigned each thread a different initial prime to perform the algorithm on. Then I had each thread increment their value by the number of threads (8), and repeated the algorithm. This allowed each thread to process one eighth of the total values. This work successfully in my testing, where all threads achieved a similar execution time.