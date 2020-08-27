package file.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

public class FileWatcher {

    private static FileWatcher fileWatcherInstance;

    public static synchronized FileWatcher getInstance() {
        if (Objects.isNull(fileWatcherInstance)) {
            fileWatcherInstance = new FileWatcher();
        }
        return fileWatcherInstance;
    }

    public WatchService createWatcherService(Path path) throws IOException {
        WatchService watchService
                = FileSystems.getDefault().newWatchService();
        registerWatcher(path, watchService);

        return watchService;
    }

    private void registerWatcher(Path path, WatchService watchService) throws IOException {
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE);
    }
}
