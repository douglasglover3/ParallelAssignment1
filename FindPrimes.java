import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

class PrimesThread extends Thread {
    int value;
    boolean primeBool[];
    double max = Math.pow(10,8);
    int sqrmax = (int)Math.sqrt(max); //Highest value needed to loop through
    public void run()
    {
        long startTime = System.currentTimeMillis();
        while (value < sqrmax) {
            for(int i = value * 2; i < max; i+= value)
                primeBool[i] = false;
            if(value == 2) //if value is two, become odd
                value += 1;
            value += 8; //skip values processed by other threads
            while(value < sqrmax && !primeBool[value]) //skip non-primes
                value += 8;
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.println("Thread " + this.threadId() + " completed in " + Long.toString(time) + " ms");
    }
}

public class FindPrimes {
    public static void main(String[] args)
    {
        
        int max = (int) Math.pow(10,8); // 10^8
        boolean primeBool[] = new boolean[max]; //List of bools indicating prime
        Arrays.fill(primeBool, true);

        int n = 8; // Number of threads
        long startTime = System.currentTimeMillis(); //time at start of execution

        PrimesThread threads[] = new PrimesThread[n];
        int[] initialPrimes = {2,3,5,7,11,13,17,19}; //starting with the first eight primes splits work evenly among the threads
        
        //cycle through each thread
        for (int i = 0; i < n; i++) { 
            threads[i] = new PrimesThread();
            threads[i].value = initialPrimes[i]; //assign first eight primes
            threads[i].primeBool = primeBool;
            threads[i].start();
        }
        //wait for each thread to finish
        for (int i = 0; i < n; i++) { 
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long executionTime = System.currentTimeMillis() - startTime; //time taken to execute

        long primeCount = 0;
        long primeSum = 0;
        long primeMax[] = new long[10];

        //Get number of primes and sum
        for(int i = 2; i < max; i++)
            if(primeBool[i])
            {
                primeCount++;
                primeSum += i;
            }
        
        // Get top ten maximum values
        int j = 0;
        for(int i = max - 1; i > 2; i--)
        {
            if(primeBool[i])
            {
                primeMax[9 - j] = i;
                j++;
            }
            if(j >= 10)
                break;
        }
    
        //Write primes.txt
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("primes.txt"));
            
            writer.write(Long.toString(executionTime) + " ms\n");
            writer.write(Long.toString(primeCount) + "\n");
            writer.write(Long.toString(primeSum) + "\n");
            for(int i = 0; i < 10; i++)
                writer.write(Long.toString(primeMax[i]) + ", ");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}