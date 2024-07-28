package phb.ebookstore.dev.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import phb.ebookstore.dev.enumric.GenreGroup;
import phb.ebookstore.dev.service.GenreService;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {
	
	@Autowired
	private GenreService genreService;

	@GetMapping("/genres")
	public ResponseEntity<?> getGenres() {
		Set<GenreGroup> allGenreGroups = GenreGroup.getAllGenreGroups();
		return ResponseEntity.ok(allGenreGroups);
	}
	
	@GetMapping("/grouped")
    public ResponseEntity<Map<String, List<String>>> getGroupedGenres() {
        Map<String, List<String>> groupedGenres = genreService.getGenresGroupedByCategory();
        return ResponseEntity.ok(groupedGenres);
    }
}
