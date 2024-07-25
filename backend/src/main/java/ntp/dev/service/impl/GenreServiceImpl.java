package ntp.dev.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ntp.dev.enumric.Genre;
import ntp.dev.enumric.GenreGroup;
import ntp.dev.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Override
	public Map<String, List<String>> getGenresGroupedByCategory() {
		Map<String, List<String>> groupedGenres = new HashMap<>();

		for (GenreGroup group : GenreGroup.getAllGenreGroups()) {
            List<String> genres = Genre.getGenresByGroup(group)
                                        .stream()
                                        .map(Genre::name)
                                        .collect(Collectors.toList());
            groupedGenres.put(group.name(), genres);
        }

        return groupedGenres;
	}

}
