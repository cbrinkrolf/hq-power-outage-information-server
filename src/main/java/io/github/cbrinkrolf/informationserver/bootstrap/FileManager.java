package io.github.cbrinkrolf.informationserver.bootstrap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class FileManager {

	public List<Path> getSortedSQLFilePaths(Path folder) {
		List<Path> files = getFilesFromFolderSortedByModDate(folder, ".sql");

		return files;

	}

	/**
	 * @param folder
	 * @param endsWith
	 * @return List with files sorted from old to new by last modified date. Could be null.
	 */
	private List<Path> getFilesFromFolderSortedByModDate(Path folder, String endsWith) {
		System.out.println(folder.getFileName());
		List<Path> result = null;
		try (Stream<Path> pathStream = Files
				.find(folder, 1, (p, basicFileAttributes) -> p.getFileName().toString().endsWith(endsWith))
				.sorted((o1, o2) -> Long.compare(o1.toFile().lastModified(), o2.toFile().lastModified()))) {
			result = pathStream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
