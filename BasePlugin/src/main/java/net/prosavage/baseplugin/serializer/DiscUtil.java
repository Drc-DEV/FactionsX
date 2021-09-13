package net.prosavage.baseplugin.serializer;

import net.prosavage.baseplugin.SavagePlugin;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiscUtil {
    // -------------------------------------------- //
    // BYTE
    // -------------------------------------------- //
    private static HashMap<String, Lock> locks = new HashMap<>();

    public static String readString(File file) throws IOException {
        int length = (int) file.length();
        byte[] output = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            offset += in.read(output, offset, (length - offset));
        }
        in.close();

        return new String(output, StandardCharsets.UTF_8);
    }

    // -------------------------------------------- //
    // STRING
    // -------------------------------------------- //

    public static void writeBytes(File file, byte[] bytes) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(new String(bytes, StandardCharsets.UTF_8).getBytes());
        out.close();
    }

    public static void write(File file, String content) throws IOException {
        writeBytes(file, utf8(content));
    }

    // -------------------------------------------- //
    // CATCH
    // -------------------------------------------- //

    public static String read(File file) throws IOException {
        return readString(file);
    }

    public static boolean writeCatch(final File file, final String content, boolean sync) {
        String name = file.getName();
        final Lock lock;

        // Create lock for each file if there isn't already one.
        if (locks.containsKey(name)) {
            lock = locks.get(name);
        } else {
            ReadWriteLock rwl = new ReentrantReadWriteLock();
            lock = rwl.writeLock();
            locks.put(name, lock);
        }

        if (sync) {
            lock.lock();
            try {
                file.createNewFile();
                final OutputStream outputStream = new FileOutputStream(file);

                try (final OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                    writer.write(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(SavagePlugin.getInstance(), () -> {
                lock.lock();
                try {
                    write(file, content);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }
        return true; // don't really care but for some reason this is a boolean.
    }

    public static String readCatch(File file) {
        try {
            return read(file);
        } catch (IOException e) {
            return null;
        }
    }

    // -------------------------------------------- //
    // UTF8 ENCODE AND DECODE
    // -------------------------------------------- //

    public static byte[] utf8(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    public static String utf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}