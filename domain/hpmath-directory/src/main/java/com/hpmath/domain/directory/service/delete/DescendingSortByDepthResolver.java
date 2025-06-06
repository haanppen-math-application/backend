package com.hpmath.domain.directory.service.delete;

import com.hpmath.domain.directory.dao.Directory;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class DescendingSortByDepthResolver {
    public List<Directory> getDecsendingList(final List<Directory> directories) {
        return directories.stream()
                .map(directory -> new DirectoryDepth(directory, this.getDepth(directory.getPath())))
                .sorted((directory1, directory2) -> directory2.depth() - directory1.depth())
                .map(directoryDepth -> directoryDepth.directory)
                .toList();
    }

    private Integer getDepth(final String path) {
        int depthCount = 0;
        for (int i = 0; i < path.length() - 1; i++) {
            if (path.charAt(i) == '/') {
                depthCount++;
            }
        }
        return depthCount;
    }

    private record DirectoryDepth(
            Directory directory,
            Integer depth) {
    }
}
