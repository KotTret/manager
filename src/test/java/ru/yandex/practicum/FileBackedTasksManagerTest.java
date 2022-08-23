package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.domain.Epic;
import ru.yandex.practicum.domain.Task;
import ru.yandex.practicum.management.task.FileBackedTasksManager;
import ru.yandex.practicum.management.task.Managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private final String s = File.separator;
    private final String pathCSV = s+"home"+s+"kot"+s+"dev"+s+"java-kanban"+s+"src"+s+"main"+s+"resources"+s+"test.csv";
    private File file = new File(pathCSV);


    @BeforeEach
    public void beforeEach() throws IOException {
        Files.writeString(Path.of(pathCSV), "");
        manager = Managers.getFileBackedTasksManager(file);
    }

    @Test
    void saveToFileAndLoadFromFileForEmptyListTest() {
        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager, manager2, "Десериализация менеджера прошла с ошибкой");
    }


    @Test
    void saveToFileAndLoadFromFileWhenEpicsWithoutSubtasksTest() {
        addTasks();
        addEpics();
        manager.getTaskById(1);
        manager.getEpicById(3);
        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager.getHistory(), manager2.getHistory(), "Десериализация менеджера прошла с ошибкой History");
        assertEquals(manager.getTasks(), manager2.getTasks(), "Десериализация менеджера прошла с ошибкой Tasks");
        assertEquals(manager.getEpics(), manager2.getEpics(), "Десериализация менеджера прошла с ошибкой Epics");
        assertEquals(manager.getSubtasks(), manager2.getSubtasks(), "Десериализация менеджера прошла с ошибкой Subtasks");
        assertEquals(manager.getId(), manager2.getId(), "Десериализация менеджера прошла с ошибкой Id");

    }

    @Test
    void saveToFileAndLoadFromFileWhenHistoryIsEmptyTest() {
        addTasks();
        addEpics();
        FileBackedTasksManager manager2 = FileBackedTasksManager.loadFromFile(file);
        assertEquals(manager.getHistory(), manager2.getHistory(), "Десериализация менеджера прошла с ошибкой History");
        assertEquals(manager.getTasks(), manager2.getTasks(), "Десериализация менеджера прошла с ошибкой Tasks");
        assertEquals(manager.getEpics(), manager2.getEpics(), "Десериализация менеджера прошла с ошибкой Epics");
        assertEquals(manager.getSubtasks(), manager2.getSubtasks(), "Десериализация менеджера прошла с ошибкой Subtasks");
        assertEquals(manager.getId(), manager2.getId(), "Десериализация менеджера прошла с ошибкой Id");
        assertEquals(manager, manager2, "Десериализация менеджера прошла с ошибкой");
    }
}
