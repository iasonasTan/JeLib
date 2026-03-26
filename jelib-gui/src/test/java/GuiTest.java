import com.je.core.Console;
import com.je.core.JeLib;
import com.je.gui.GuiUtils;
import org.junit.Test;

import java.util.function.Consumer;

public class GuiTest {
    @Test
    public void guiUtils() {
        JeLib.console().setEnabled(Console.Type.EXCEPTION, false);
        // Generate a big stack that throws an exception in the bottom.
        try {
            Consumer<Integer> thrower = new Consumer<>() {
                @Override
                public void accept(Integer integer) {
                    if (integer <= 0)
                        // noinspection all
                        Integer.parseInt("a");
                    else
                        accept(--integer);
                }
            };
            thrower.accept(20);
        } catch (NumberFormatException e) {
            GuiUtils.showException(e);
        }
    }
}
