package file.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

public class FileWatcher {

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
