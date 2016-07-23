package org.to2mbn.lolixl.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import org.to2mbn.lolixl.utils.internal.GsonFactory;
import com.google.gson.JsonSyntaxException;

public final class GsonUtils {

	public static <T> T fromJson(Path file, Class<T> type) throws JsonSyntaxException, IOException {
		try (Reader reader = new InputStreamReader(Files.newInputStream(file), "UTF-8")) {
			return GsonFactory.instance.fromJson(reader, type);
		}
	}

	public static void toJson(Path file, Object obj) throws JsonSyntaxException, IOException {
		PathUtils.tryMkdirsParent(file);
		try (Writer writer = new OutputStreamWriter(Files.newOutputStream(file), "UTF-8")) {
			GsonFactory.instance.toJson(obj, writer);
		}
	}

}