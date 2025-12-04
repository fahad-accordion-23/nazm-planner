package nazmplanner.infrastructure.persistence.calendars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import nazmplanner.domain.calendars.CalendarEvent;
import nazmplanner.domain.calendars.CalendarEventRepository;

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
import java.util.UUID;

/**
 * <h2>JSONCalendarEventRepository</h2>
 * 
 * <p>Implementation of CalendarEventRepository using JSON persistence.</p>
 * <p>Note: I generated this using AI for prototyping. Will be replaced by SQLite</p>
 * 
 * @author Gemini
 * @version 23/11/2025
 */
public class JSONCalendarEventRepository implements CalendarEventRepository
{
    private final File databaseFile;
    private final Gson gson;

    public JSONCalendarEventRepository(String filePath)
    {
        this.databaseFile = new File(filePath);
        
        // Reuse the same Gson configuration logic as Tasks
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
                writeList(new ArrayList<>()); // Initialize with empty list
            }
        }
        catch (IOException e)
        {
            System.err.println("FATAL: Could not initialize events file: " + databaseFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    // --- CRUD Implementation ---

    @Override
    public void save(CalendarEvent event)
    {
        List<CalendarEvent> allEvents = getAll();
        
        // Update if exists, otherwise add
        boolean updated = false;
        for (int i = 0; i < allEvents.size(); i++)
        {
            if (allEvents.get(i).getId().equals(event.getId()))
            {
                allEvents.set(i, event);
                updated = true;
                break;
            }
        }
        
        if (!updated)
        {
            allEvents.add(event);
        }
        
        writeList(allEvents);
    }

    @Override
    public List<CalendarEvent> getAll()
    {
        try (Reader reader = new FileReader(databaseFile))
        {
            Type listType = new TypeToken<ArrayList<CalendarEvent>>(){}.getType();
            List<CalendarEvent> events = gson.fromJson(reader, listType);
            
            return events != null ? events : new ArrayList<>();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public CalendarEvent getById(UUID id) // Fixed signature to accept ID
    {
        return getAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(UUID id)
    {
        List<CalendarEvent> allEvents = getAll();
        boolean removed = allEvents.removeIf(e -> e.getId().equals(id));
        
        if (removed)
        {
            writeList(allEvents);
        }
    }

    // --- Helpers ---

    private void writeList(List<CalendarEvent> events)
    {
        try (Writer writer = new FileWriter(databaseFile))
        {
            gson.toJson(events, writer);
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
            if (value == null) out.nullValue();
            else out.value(value.toString());
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
}