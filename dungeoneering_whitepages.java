
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	public static void main(String args[] ) throws Exception {
		/* Enter your code here. Read input from STDIN. Print output to STDOUT */
		Scanner scan = new Scanner(System.in);
		List input = new ArrayList();
		while (scan.hasNext()) {
			input.add(scan.nextInt());
		}

		//calls canyonFlight
		List output = canyonFlight(input);
		if (output == null) {
			System.out.println("failure");
		} else {
			output = optimize(input, output);
			int index = 0;
			while(index < output.size()) {
				//here we check if we chose minimum path
				System.out.print(output.get(index) + ", ");
				index++;
			}
			System.out.println("out");
		}
	}

	//actual method
	public static List canyonFlight(List input) {
		if (input.size() <= 1 || input.get(0) == 0) {
			//not possible to complete
			return null;
		}
		List flights = new ArrayList(); //this will be what we eventually return

		int steps = input.get(0); //"stepping" through each flight
		int max = input.get(0); //tells us the farthest index we can get with current flight

		for (int i = 0; i < input.size(); i++) {
			steps--; //needed to use a step to get to current index
			max = Math.max(max, i + input.get(i)); //compares with previous max and current
			steps = max - i;
			//adds the flight if current index increased max
			if (max == i + input.get(i)) {
				flights.add(i);
			}
			if (steps == 0) {
				if (input.get(i) == 0) {
					//System.out.println("dragon at " + i);
					return null; //we landed on dragon
				}
				//steps = max - i; //new number of steps we need
				if (steps <= 0) {
					//System.out.println("bad steps");
					return null; //another dragon
				}
			}
		}
		return flights;
	}

	//takes current flights and makes sure they're minimum
	public static List optimize(List input, List flights) {
		int i = 1;
		while (i < flights.size() - 1) {
			//removes flights accessable from the prior flight
			int currIndex = flights.get(i);
			int pastIndex = flights.get(i - 1);
			if (currIndex - i < 0) {
				//System.out.println("negative index");
				i++;
			} else if (flights.get(i + 1) <= (input.get(pastIndex))) {
				//System.out.println("removed " + flights.get(i));
				flights.remove(i);
			} else {
				//System.out.println("last else, currIndex - i is " + (currIndex - i) + " and input is " + input.get(currIndex - i));
				i++;
			}
		}
		return flights;
	}
}