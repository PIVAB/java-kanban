package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static model.TaskStatus.*;
import service.*;

class SubtaskTest {

    @Test
    void subtaskEqualsIfIdEquals() {
        Epic epic = new Epic("Test addNewSubtask",
                "Test addNewSubtask description");
        TaskManager taskManager = Managers.getDefault();
        taskManager.makeNewEpic(epic);
        Subtask subTask = new Subtask("Test addNewTask",
                "Test addNewTask description", NEW, epic.getId());
        taskManager.makeNewSubtask(subTask);

        int subTaskId = subTask.getId();

        assertEquals(taskManager.getSubtaskById(subTaskId), taskManager.getSubtaskById(subTaskId),
                "Подзадачи с одинаковым id не совпадают");
    }

    @Test
    void shouldNotMakeSubtaskAsItselfEpic() {
        TaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test shouldNotMakeSubtaskAsEpic",
                "Test shouldNotMakeSubtaskAsEpic description");
        taskManager.makeNewEpic(epic);
        Subtask subTask = new Subtask("Test shouldNotMakeSubtaskAsEpic",
                "Test shouldNotMakeSubtaskAsEpic description", NEW, epic.getId());
        taskManager.makeNewSubtask(subTask);
        Epic epic1 = new Epic("Test shouldNotMakeSubtaskAsEpic",
                "Test shouldNotMakeSubtaskAsEpic description");
        taskManager.makeNewEpic(epic1);

        assertNotEquals(subTask.getId(), epic1.getId());
    }
}