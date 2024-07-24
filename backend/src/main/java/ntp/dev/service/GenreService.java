package ntp.dev.service;

import java.util.List;
import java.util.Map;

public interface GenreService {
	public Map<String, List<String>> getGenresGroupedByCategory();
}
