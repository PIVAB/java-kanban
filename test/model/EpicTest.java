package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import service.*;

class EpicTest {

    static final TaskManager taskManager = Managers.getDefault();

    @Test
    void addEpic() {
        taskManager.addNewEpic(new Epic("NEW_EPIC"));
        final Epic savedEpic = taskManager.getEpicById(1);

        assertEquals("NEW_EPIC", savedEpic.getTitle());
    }
}