package terminal.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Keybinds {
    /** Default Bindings */
    private static final Map<ArrayList<Integer>, Action> BINDS = Map.of(
            new ArrayList<>(List.of(27, 91, 65)), Action.UP,
            new ArrayList<>(List.of(27, 91, 66)), Action.DOWN,
            new ArrayList<>(List.of(13)), Action.ENTER,
            new ArrayList<>(List.of(127)), Action.EXIT,
            new ArrayList<>(List.of(27, 79, 82)), Action.DEBUG
    );

    /** The Action bound to the defined key code */
    public static Action get(ArrayList<Integer> key) {return BINDS.get(key);}
}