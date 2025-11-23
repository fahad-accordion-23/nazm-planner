package nazmplanner.infrastructure.persistence.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskRepository;

/**
 * <h2>JSONTaskRepository</h2>
 *
 * <p>Implementation of TaskRepository that persists tasks to a JSON file.</p>
 * <p>Requires the Google Gson library.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class JSONTaskRepository implements TaskRepository
{
    
    private final File databaseFile;
    private final Gson gson;

    public JSONTaskRepository(String filePath)
    {
        this.databaseFile = new File(filePath);
        
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        
        initFile();
    }

    private void initFile()
    {
        try
        {
            File parentDir = databaseFile.getParentFile();
            
            if (parentDir != null && !parentDir.exists())
            {
                parentDir.mkdirs();
            }
            
            if (!databaseFile.exists())
            {
                databaseFile.createNewFile();
                saveAll(new ArrayList<>());
            }
        }
        catch (IOException e)
        {
            System.err.println("FATAL: Could not initialize task repository file: " + databaseFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll()
    {
        try (Reader reader = new FileReader(databaseFile))
        {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            List<Task> tasks = gson.fromJson(reader, listType);
            
            return tasks != null ? tasks : new ArrayList<>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Task task)
    {
        List<Task> allTasks = findAll();
        
        // Check if task exists and update it, otherwise add new
        Optional<Task> existingTask = allTasks.stream()
                .filter(t -> t.getID().equals(task.getID()))
                .findFirst();

        if (existingTask.isPresent())
        {
            int index = allTasks.indexOf(existingTask.get());
            allTasks.set(index, task);
        }
        else
        {
            allTasks.add(task);
        }

        saveAll(allTasks);
    }

    private void saveAll(List<Task> tasks)
    {
        try (Writer writer = new FileWriter(databaseFile))
        {
            gson.toJson(tasks, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime>
    {
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException
        {
            if (value == null)
            {
                out.nullValue();
            }
            else
            {
                out.value(value.toString());
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException
        {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL)
            {
                in.nextNull();
                return null;
            }
            return LocalDateTime.parse(in.nextString());
        }
    }
    
    @Override
    public Task findById(UUID id)
    {
        return findAll().stream()
                .filter(t -> t.getID().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public void delete(UUID id)
    {
        List<Task> allTasks = findAll();
        
        // Remove the task with the matching ID
        boolean removed = allTasks.removeIf(t -> t.getID().equals(id));
        
        if (removed)
        {
            saveAll(allTasks);
        }
    }
    
}