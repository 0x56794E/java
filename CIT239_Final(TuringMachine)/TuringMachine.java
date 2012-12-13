package turingmachine;
import java.util.*;
import java.io.File;
/**
 *
 * @author Vy Thuy Nguyen
 */
public class TuringMachine {

    StateTable stateTable;
    Tape tape;
    SymbolMap symbolMap;
    
    int stateCount;
    int machineState;
   

    public TuringMachine(File file, String input) throws Exception
    {
        readFromFile (file); //initialize symbolMap and stateCount and stateTable

        tape = new Tape(input);
    }

    private void readFromFile(File file) throws Exception
    {
        Scanner input = new Scanner(file);
        String currentLine;
        boolean stop = false;
        while (input.hasNextLine() && !stop)
        {
            currentLine = input.nextLine();
            if (currentLine.isEmpty())
                continue;
            switch(currentLine.charAt(0))
            {
               
                case '@':
                    symbolMap = new SymbolMap(currentLine.substring(1));
                    break;
                case '#':
                    stateCount = Integer.parseInt(currentLine.substring(1));
                    stop = true;
                    break;
                default:
                    break;
            }
        }

        stateTable = new StateTable(stateCount, symbolMap.getSymbolCount());
                 
        while (input.hasNextLine())
        {
            currentLine = input.nextLine();
            if (currentLine.isEmpty())
                continue;
            if (currentLine.charAt(0) == ':')
            {
                    Scanner scanner = new Scanner(currentLine);
                    int state = Integer.parseInt((scanner.next()).substring(1));
                    char symbol = scanner.next().charAt(0);
                    char newSymbol = scanner.next().charAt(0);
                    int movement = scanner.nextInt();
                    int newState = scanner.nextInt();
                    Action newAc = new Action(newSymbol, movement,newState);
                    stateTable.setAction(state,symbolMap.getSymbol(symbol),newAc);

             }
        }
    }

    public static void  main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        File file;

        do
        {
            System.out.print("Enter the filename... ");
            String filename = input.next();
            file = new File(filename);

            if (!file.exists())
                System.err.println("File Not Found!");
        }
        while (!file.exists());
        
        System.out.println("Enter input...");
        String inputStr = input.next();
        if (inputStr.isEmpty())
            System.exit(0);
       
        TuringMachine machine = new TuringMachine(file, inputStr);

        machine.run();
    }

    public void run()
    {         
        tape.printTape();
        
        char currentSym = tape.getSymbolChar();
        int currentState = tape.getState();
        
    do
    {
        char newSym = (stateTable.getAction(currentState, symbolMap.getSymbol(currentSym))).getNewSymbol();

        //change the cell to the new symbol
        char dummy = tape.activeTape.set(tape.getSymbol(), newSym);

        //move the head
        int newHeadIndex = tape.getHeadPosition() + stateTable.getAction(currentState, symbolMap.getSymbol(currentSym)).getMovement();
        if (newHeadIndex >= tape.activeTape.size())
            tape.activeTape.add(symbolMap.getBlank());
        if (newHeadIndex < 0)
            tape.activeTape.add(0, symbolMap.getBlank());
        
        tape.setHeadPosition((newHeadIndex < 0 ? 0 : newHeadIndex));

        //set state of the machine
        int newState = stateTable.getAction(currentState, symbolMap.getSymbol(currentSym)).getnewState();
        tape.setState(newState);


        tape.printTape();

        currentState = tape.getState();
        currentSym = tape.getSymbolChar();

        } while(symbolMap.isInAlphabet(currentSym) && currentState >= 0 && currentState < stateCount
                && stateTable.getAction(currentState, symbolMap.getSymbol(currentSym)) != null);
}
}