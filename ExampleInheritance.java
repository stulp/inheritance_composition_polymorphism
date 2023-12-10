import java.util.Stack;

public class ExampleInheritance {





public static class MyState {
    private int x = 0;
    
    public void setX(int x) { this.x = x; }
    public int getX() { return x; }
}

public static interface Command {
    public void apply(MyState state);
    public void undo(MyState state);
}

public static class MyStateUndo extends MyState { // Inheritance
    private Stack<Command> commands_done = new Stack<>();


    public void execute(Command command) {
        command.apply(this);
        commands_done.add(command);
    }
    public void undo() {
        if (this.commands_done.isEmpty())
            return; // Stack is empty; cannot undo.
        Command command = commands_done.pop();
        command.undo(this);
    }
    
    
    // getX and setX inherited from MyState
    
    
}

public static class GUI {
    public void plotState(MyState state) {
        System.out.println(state.getX()); // Fancy plotting ;-)
    }
}









public static void main(String arguments[]) {
    GUI gui = new GUI();
    
    MyState state1 = new MyState();
    state1.setX(8);
    gui.plotState(state1);

    MyStateUndo state2 = new MyStateUndo();
    state2.execute(new CommandSet(2, "player1"));
    state2.execute(new CommandSet(4, "player2"));
    state2.undo(); // Undo last command by player2 (for whatever reason)
    state2.execute(new CommandMagic("player2")); // Brilliant mode: magic command!
    gui.plotState(state2);
    
    MyStateUndo state3 = new MyStateUndo();
    state3.execute(new CommandSet(3, "player1"));
    state3.execute(new CommandSet(6, "player2"));
    state3.setX(20); // Hey, who is this? Certainly not one of the players!
    state3.undo(); // What is the result now: weird to undo player2's move on the 20 above!!
    gui.plotState(state3);
}

public static class CommandSet implements Command {
    final private int state_after;
    private int state_before;
    final private String player_name;
    public CommandSet(int value, String player_name) {
        this.state_after = value;
        this.player_name = player_name;
    }
    public void apply(MyState state) { 
        this.state_before = state.getX();
        state.setX(this.state_after); 
    }
    public void undo(MyState state)  { 
        state.setX(this.state_before); 
    }
}

public static class CommandMagic implements Command {
    final private String player_name;
    public CommandMagic(String player_name) {
        this.player_name = player_name;
    }
    public void apply(MyState state) { 
        state.setX(state.getX()*state.getX()); 
    }
    public void undo(MyState state)  { 
        state.setX((int)Math.sqrt(state.getX())); // undoing squaring is taking square root 
    }
}
}
