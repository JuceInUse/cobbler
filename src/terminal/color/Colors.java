package terminal.color;

import java.util.ArrayList;
import java.util.List;

public class Colors {
    private static final List<Color> COLORS = new ArrayList<>();

    public static final Color ERROR = register(new Color(243, 58, 34));
    public static final Color SUCCESS = register(new Color(41, 243, 45));

    private static Color register(Color color) {
        COLORS.add(color);
        return color;
    }
}
