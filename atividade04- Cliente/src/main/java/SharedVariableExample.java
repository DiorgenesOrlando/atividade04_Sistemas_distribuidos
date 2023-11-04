public class SharedVariableExample {

    public static  void main(String[] args) {
    
    	int totalSeconds = 150; // Replace this with your actual number of seconds
    	int minutes = totalSeconds / 60;
    	int remainingSeconds = totalSeconds % 60;

    	System.out.println(totalSeconds + " seconds is equal to " + minutes + " minutes and " + remainingSeconds + " seconds.");

    }
    }
