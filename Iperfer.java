
public class Iperfer {
    public static void main(String[] args) {
        if (args.length == 7){
            if (!args[0].equals("-c") || !args[1].equals("-h") || !args[3].equals("-p") || !args[5].equals("-t")){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
        }
        else if (args.length == 3){
            if (!args[0].equals("-s") || !args[1].equals("-p")){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
        }
        else {
            System.out.println("Error: missing or additional arguments");
            System.exit(1);
        }
    }
}