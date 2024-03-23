package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static model.TaskStatus.*;
import service.*;

class EpicTest {

    @Test
    void epicEqualsIfIdEquals() {
        Epic epic = new Epic("Test epicEqualsIfIdEquals",
                "Test epicEqualsIfIdEquals description");
        TaskManager taskManager = Managers.getDefault();
        taskManager.makeNewEpic(epic);
        int epicId = epic.getId();

        assertEquals(taskManager.getEpicById(epicId), taskManager.getEpicById(epicId),
                "Эпики с одинаковым id не совпадают");
    }

    @Test
    void shouldNotAddEpicInItselfAsSubtask() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test shouldNotAddEpicInEpic",
                "Test shouldNotAddEpicInEpic description");
        taskManager.makeNewEpic(epic);
        Subtask subtask = new Subtask("Test shouldNotAddEpicInEpic",
                "Test shouldNotAddEpicInEpic description", NEW, epic.getId());
        taskManager.makeNewSubtask(subtask);

        assertNotEquals(epic.getId(), subtask.getId());

    }
}